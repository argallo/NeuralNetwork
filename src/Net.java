import java.util.ArrayList;


public class Net {


	private Layer[] layers;
	private double error;
	private double recentAverageError;
	private double recentAverageSmoothingFactor;
	
	public Net(final ArrayList<Integer> topology){
		layers = new Layer[topology.size()];
		for(int layerNum = 0; layerNum < layers.length; layerNum++){
			layers[layerNum] = new Layer(topology.get(layerNum), (layerNum+1 == layers.length) ? 0 : topology.get(layerNum+1));
		}
		
	}
	
	public void feedForward(ArrayList<Double> inputValues){
		for (int i = 0; i < inputValues.size(); i++){
			layers[0].getNeurons()[i].setOutputValue(inputValues.get(i));
		}
		
		for(int layerNum = 1; layerNum < layers.length; layerNum++){
			//Layer previousLayer = layers[layerNum-1];
			for(int node = 0; node < layers[layerNum].getNeurons().length; node++){
				//System.out.println(node);
				layers[layerNum].getNeurons()[node].feedForward(layers[layerNum-1]);
			}
		}
	}
	
	public void backProp(ArrayList<Double> targetValues){
		Layer outputLayer = layers[layers.length-1];
		error = 0.0;
		
		for(int node = 0; node < outputLayer.getNeurons().length; node++){
			double delta = targetValues.get(node) - outputLayer.getNeurons()[node].getOutputValue();
			error += delta * delta;
		}
		
		
		
		error /= outputLayer.getNeurons().length;
		error = Math.sqrt(error);
		
		recentAverageError = (recentAverageError * recentAverageSmoothingFactor + error) / 
				(recentAverageSmoothingFactor + 1.0);
		
		for(int node = 0; node < outputLayer.getNeurons().length; node++){
			outputLayer.getNeurons()[node].calculateOutputGradients(targetValues.get(node));
		}
		
		//gradients on hidden layers
		for(int layerNum = layers.length -2; layerNum > 0; layerNum--){
			//Layer hiddenLayer = layers[layerNum];
			//Layer nextLayer = layers[layerNum+1];
			
			for(int node = 0; node < layers[layerNum].getNeurons().length; node++){
				layers[layerNum].getNeurons()[node].calculateHiddenGradients(layers[layerNum+1]);
			}
		}
		
		
		//update weights 
		for(int layerNum = layers.length -1; layerNum > 0; layerNum--){
			//Layer layer = layers[layerNum];
			//Layer previousLayer = layers[layerNum -1];
			
			for(int node = 0; node < layers[layerNum].getNeurons().length; node++){
				layers[layerNum].getNeurons()[node].updateInputWeights(layers[layerNum -1]);
			}
		}
	}
	
	public ArrayList<Double> getResults(){
		ArrayList<Double> results = new ArrayList<Double>();
		for(int node = 0; node < layers[layers.length-1].getNeurons().length; node++){
			//System.out.println(layers[layers.length-1].getNeurons()[node].getOutputValue());
			results.add(layers[layers.length-1].getNeurons()[node].getOutputValue());
		}
		return results;
	}

	public double getRecentAverageError() {
		return recentAverageError;
	}
	
}
