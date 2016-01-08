import java.util.ArrayList;
import java.util.Random;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		blackjack();
		//coinflip();
	}
	
	public static void coinflip(){
	ArrayList<Integer> topology = new ArrayList<Integer>();
		
		topology.add(10000);
		topology.add(10);
		topology.add(1);
		Net myNet = new Net(topology);
		int trainingPass = 0;
		
		int correct = 0;
		ArrayList<Double> inputValues = new ArrayList<Double>();
		for(int i =0; i < 10000; i++){
			inputValues.add(Math.random());
		}
		
		while(trainingPass < 1000000){
			trainingPass ++;
			myNet.feedForward(inputValues);
			
			ArrayList<Double> targetVals = new ArrayList<Double>();
			double target = Math.random();
			
			targetVals.add(target);
			myNet.backProp(targetVals);
			
			ArrayList<Double> resultVals = myNet.getResults();
			
			double result = resultVals.get(0);
			if(target<= 0.46 && result <=0.46){
				correct++;
			} else if(target < 0.94 && result <0.94 && target > 0.46 && result > 0.46){
				correct++;
			} else if(target>=0.94 && result>=0.94){
				correct++;
			}
			
			inputValues.remove(0);
			inputValues.add(target);
			
		}
		System.out.println("Correct: "+correct);
		
	}
	
	public static void blackjack(){
		ArrayList<Integer> topology = new ArrayList<Integer>();
		
		topology.add(3);
		topology.add(10);
		//topology.add(10);
		//topology.add(10);
		topology.add(1);
		
		Net myNet = new Net(topology);
		
		Blackjack blackjack = new Blackjack();
		
		int trainingPass = 0;
		
		int correct = 0;
		
		while(trainingPass < 1000000){
		trainingPass ++;
		blackjack.reset();
		blackjack.shuffle();
		blackjack.deal();
		
		int card1 = (blackjack.card1 == 1)? 11 : blackjack.card1;
		int card2 = (blackjack.card2 == 1)? 11 : blackjack.card2;
		int dCard = (blackjack.dCard == 1)? 11 : blackjack.dCard;
		int hdCard = (blackjack.hdCard == 1)? 11 : blackjack.hdCard;
		
				
		//System.out.println("Your Cards are: "+card1+", "+card2);
		//System.out.println("Dealer Card: "+dCard+" Hidden Card: "+hdCard);
		
		
		ArrayList<Double> inputValues = new ArrayList<Double>();
	
		inputValues.add(((blackjack.playerLowHand()-2)/20.0));
		inputValues.add(((blackjack.playerHighHand()-2)/21.0));
		inputValues.add(((blackjack.opponentCard()-1)/11.0));
		//System.out.println("inputValues: "+inputValues.get(0)+", "+inputValues.get(1)+", "+inputValues.get(2));
		
		myNet.feedForward(inputValues);
		
		ArrayList<Double> targetVals = new ArrayList<Double>();
		double target = blackjack.cheaterDecide();
		//System.out.println((target==0.0)?"hit":"stand");
		targetVals.add(target);
		myNet.backProp(targetVals);
		
		ArrayList<Double> resultVals = myNet.getResults();
		//System.out.println("Outputs: "+resultVals.get(0));
		double result = resultVals.get(0)<=0.5?0:1;
		if(result == target)
			correct++;
		//System.out.println(result==target?"Correct":"Incorrect\n");
		
		
		//System.out.println("Net recent average error: "+ myNet.getRecentAverageError());
		}
		System.out.println("Correct: "+correct);
		
	}

}
