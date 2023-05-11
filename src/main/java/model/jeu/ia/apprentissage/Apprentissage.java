package main.java.model.jeu.ia.apprentissage;

import java.io.IOException;

import javax.swing.ImageIcon;

import org.deeplearning4j.rl4j.learning.configuration.QLearningConfiguration;
import org.deeplearning4j.rl4j.learning.sync.qlearning.discrete.QLearningDiscreteDense;
import org.deeplearning4j.rl4j.network.NeuralNetOutput;
import org.deeplearning4j.rl4j.network.configuration.DQNDenseNetworkConfiguration;
import org.deeplearning4j.rl4j.network.dqn.DQN;
import org.deeplearning4j.rl4j.network.dqn.DQNFactoryStdDense;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.learning.config.Adam;

import main.java.model.bdd.Profil;
import main.java.model.jeu.ECouleurJoueur;
import main.java.model.jeu.Joueur;
import main.java.model.jeu.ia.IAFacile;
import main.java.model.jeu.partie.Partie;

public class Apprentissage {

	public static void main(String[] args) throws IOException {
		// Définir l'environnement de votre jeu
		Joueur joueur1 = new Joueur(ECouleurJoueur.ROUGE, "IApprentissage", "IApprentissage", "IApprentissage");
		IAFacile joueur2 = new IAFacile(ECouleurJoueur.VERT, new Profil("IAdversaire", "IAdversaire", new ImageIcon("IAdversaire")));
		Partie partie = new Partie(joueur1, joueur2);

		// Créer le MDP
		MDPJeu mdp = new MDPJeu(partie, joueur1, joueur2);

		// Configurer l'agent
		QLearningConfiguration config = QLearningConfiguration.builder().seed(123L).maxEpochStep(1000).maxStep(15000)
				.expRepMaxSize(150000).batchSize(128).targetDqnUpdateFreq(500).updateStart(10).rewardFactor(0.01)
				.gamma(0.99).errorClamp(1.0).minEpsilon(0.1f).epsilonNbStep(1000).doubleDQN(true).build();

		// Créer l'instance QLearning
		QLearningDiscreteDense<EtatPartie> dql = new QLearningDiscreteDense<>(mdp, buildDQNFactory(), config);

		// Entraîner l'agent
		System.out.println("ok");
		dql.train();
		// get the final policy
		dql.getPolicy().save("dql.model"); // save the model at the end
		// Entrée d'exemple
		double[] stateArray = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
		INDArray state = Nd4j.create(stateArray).reshape(1,34);

		// Calculer la sortie pour l'entrée donnée
		NeuralNetOutput output = dql.getNeuralNet().output(state);

		double[] stateArray2 = new double[15];
		INDArray state2 = Nd4j.create(stateArray2).reshape(1,15);
		INDArray[] res = dql.getNeuralNet().outputAll(state);
		for(INDArray a:res) {
			System.out.println(a.toString());
		}
		mdp.close();

		System.out.println("ok");


	}

	public static DQN buildDQNFactory() {
		final DQNDenseNetworkConfiguration build = DQNDenseNetworkConfiguration.builder().l2(0.001)
				.updater(new Adam(0.01)).numHiddenNodes(300).numLayers(2).build();
		DQNFactoryStdDense factory = new DQNFactoryStdDense(build);
		int[] inputShape = {34};
		return factory.buildDQN(inputShape, 15);
	}

}
