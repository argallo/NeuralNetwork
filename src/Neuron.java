import java.util.ArrayList;
import java.util.Random;


public class Neuron {
	
	private double outputValue;
	private ArrayList<Connections> outputWeights;
	private Random r = new Random();
	private int myIndex;
	private double gradient;
	static double eta = 0.15;
	static double alpha = 0.5;

	public Neuron(int numOutputs, int myIndex){
		this.myIndex = myIndex;
		outputWeights = new ArrayList<Connections>();
		for(int i = 0; i < numOutputs; i++){
			outputWeights.add(new Connections(randomWeight()));
		}
	}
	
	public double randomWeight(){
		return r.nextDouble();
	}

	public void feedForward(Layer previousLayer) {
		double sum = 0.0;
		for(int node = 0; node < previousLayer.getNeurons().length; node++){
			sum += previousLayer.getNeurons()[node].getOutputValue() * previousLayer.getNeurons()[node].getOutputWeights().get(myIndex).getWeight();
		}
		
		outputValue = transferFunction(sum);
		
		if(myIndex == 0){
			//System.out.println(outputValue);
		}
	}

	private static double transferFunction(double sum) {
		return Math.tanh(sum);
	}
	
	private static double transferFunctionDerivative(double sum) {
		return 1.0 - sum * sum;
	}

	public void setOutputValue(Double val) {
		outputValue = val;
	}
	
	public double getOutputValue() {
		return outputValue;
	}
	
	public ArrayList<Connections> getOutputWeights() {
		return outputWeights;
	}

	public void calculateOutputGradients(Double targetValue) {
		double delta = targetValue - outputValue;
		gradient = delta * transferFunctionDerivative(outputValue);
	}

	public void calculateHiddenGradients(Layer nextLayer) {
		double dow = sumDOW(nextLayer);
		gradient = dow * transferFunctionDerivative(outputValue);
	}

	private double sumDOW(Layer nextLayer) {
		double sum = 0.0;
		for(int node = 0; node < nextLayer.getNeurons().length; node++){
			sum += outputWeights.get(node).getWeight() * nextLayer.getNeurons()[node].gradient;
		}
		return sum;
	}

	public void updateInputWeights(Layer previousLayer) {
		for(int node = 0; node < previousLayer.getNeurons().length; node++){
			Neuron neuron = previousLayer.getNeurons()[node];
			double oldDeltaWeight = neuron.outputWeights.get(myIndex).getDeltaWeight();
			double newDeltaWeight = eta * neuron.getOutputValue() * gradient + alpha * oldDeltaWeight;
			
			neuron.getOutputWeights().get(myIndex).setDeltaWeight(newDeltaWeight);
			neuron.getOutputWeights().get(myIndex).setWeight(neuron.getOutputWeights().get(myIndex).getWeight() + newDeltaWeight);
		}
		
	}
	
	
}
