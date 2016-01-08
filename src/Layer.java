import java.util.ArrayList;


public class Layer {
	
	Neuron[] neurons;

	public Layer(int neuronSize, int layerOutputNum){
		neurons = new Neuron[neuronSize];
		for(int neuronNum = 0; neuronNum < neuronSize; neuronNum++){
			neurons[neuronNum] = new Neuron(layerOutputNum, neuronNum);
		}
	}
	
	public Neuron[] getNeurons() {
		return neurons;
	}
	
}
