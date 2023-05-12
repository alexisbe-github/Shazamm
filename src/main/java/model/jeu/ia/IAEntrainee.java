package main.java.model.jeu.ia;

import java.io.IOException;
import java.util.Arrays;

import org.deeplearning4j.rl4j.learning.configuration.QLearningConfiguration;
import org.deeplearning4j.rl4j.learning.sync.qlearning.discrete.QLearningDiscreteDense;
import org.deeplearning4j.rl4j.network.NeuralNetOutput;
import org.deeplearning4j.rl4j.network.configuration.DQNDenseNetworkConfiguration;
import org.deeplearning4j.rl4j.network.dqn.DQN;
import org.deeplearning4j.rl4j.network.dqn.DQNFactoryStdDense;
import org.deeplearning4j.rl4j.policy.DQNPolicy;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.learning.config.Adam;

import main.java.model.jeu.ECouleurJoueur;
import main.java.model.jeu.Joueur;
import main.java.model.jeu.carte.Carte;
import main.java.model.jeu.ia.apprentissage.EtatPartie;
import main.java.model.jeu.ia.apprentissage.MDPJeu;
import main.java.model.jeu.partie.Partie;
import main.java.model.jeu.partie.Tour;
import main.java.vue.ILancementStrategy;

public class IAEntrainee extends IAEtatJeu implements IA, ILancementStrategy {

	private QLearningDiscreteDense<EtatPartie> dql;

	public IAEntrainee(ECouleurJoueur couleur, String nom, String prenom, String avatar) {
		super(couleur, nom, prenom, avatar);
		// Définir l'environnement de votre jeu
		Joueur joueur1 = new Joueur(ECouleurJoueur.ROUGE, "IApprentissage", "IApprentissage", "IApprentissage");
		IAFacile joueur2 = new IAFacile(ECouleurJoueur.VERT, "IAdversaire", "IAdversaire", "IAdversaire");
		Partie partie = new Partie(joueur1, joueur2);

		// Créer le MDP
		MDPJeu mdp = new MDPJeu(partie, joueur1, joueur2);

		// Configurer l'agent
		QLearningConfiguration config = QLearningConfiguration.builder().seed(12L).maxEpochStep(10000).maxStep(200)
				.expRepMaxSize(150000).batchSize(128).targetDqnUpdateFreq(500).updateStart(10).rewardFactor(0.01)
				.gamma(0.99).errorClamp(1.0).minEpsilon(0.1f).epsilonNbStep(1000).doubleDQN(true).build();

		// Créer l'instance QLearning
		dql = new QLearningDiscreteDense<>(mdp, buildDQNFactory(), config);

		// Entraîner l'agent
		System.out.println("L'IA commence à s'entraîner...");
		dql.train();

		System.out.println("Entraînement fini!");
		// get the final policy
		try {
			dql.getPolicy().save("dql.model");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // save the model at the end

		mdp.close();

	}

	@Override
	public void jouerTour(Partie p) {

		double[] stateArray = new double[15];
		stateArray[0] = this.getManaActuel();
		for (Carte c : this.getMainDuJoueur()) {
			stateArray[c.getNumeroCarte()] = 1;
		}
		// Calculer la sortie pour l'entrée donnée
		INDArray state = Nd4j.create(stateArray).reshape(1, 15);

		// Calculer la sortie pour l'entrée donnée
		NeuralNetOutput output = dql.getNeuralNet().output(state);

		double[] stateArray2 = new double[15];
		INDArray state2 = Nd4j.create(stateArray2).reshape(1, 15);
		INDArray[] res = dql.getNeuralNet().outputAll(state);
		double[] resultats = new double[15];
		for (INDArray a : res) {
			for (int i = 0; i < 15; i++) {
				resultats[i] = a.getDouble(i);
			}
		}
		resultats[0] = 1 / (1 + Math.exp(-resultats[0]));
		int mise = (int) (resultats[0] * 50);
		System.out.println(Arrays.toString(resultats));
		double max = -1;
		int indiceMax = -1;
		for (int i = 1; i < 15; i++) {
			if (resultats[i] > max && this.possedeLaCarteDansSaMain(i)) {
				indiceMax = i;
				max = resultats[i];
			}
		}

		Carte carteAJouer = null;
		if (indiceMax > 0) {
			for (Carte c : this.getMainDuJoueur()) {
				if (c.getNumeroCarte() == indiceMax) {
					carteAJouer = c;
				}
			}
		}
		if (carteAJouer != null) {
			p.jouerCarte(carteAJouer, this);
		}
		p.getMancheCourante().getTourCourant().setMiseJoueur(this, mise);
	}

	@Override
	public void lancerClone(Partie p, Tour tour, Joueur joueur) {
		new SimulationStrategyLancementSort().lancerClone(p, tour, joueur);

	}

	@Override
	public void lancerRecyclage(Partie p, Tour tour, Joueur joueur) {
		new SimulationStrategyLancementSort().lancerRecyclage(p, tour, joueur);
	}

	@Override
	public void lancerLarcin(Partie p, Tour tour, Joueur joueur) {
		new SimulationStrategyLancementSort().lancerLarcin(p, tour, joueur);
	}

	public static DQN buildDQNFactory() {
		final DQNDenseNetworkConfiguration build = DQNDenseNetworkConfiguration.builder().l2(0.001)
				.updater(new Adam(0.01)).numHiddenNodes(300).numLayers(3).build();
		DQNFactoryStdDense factory = new DQNFactoryStdDense(build);
		int[] inputShape = { 15 };
		return factory.buildDQN(inputShape, 15);
	}

}
