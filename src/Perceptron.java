import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
/**
 * Perceptron class performs all steps involve to carry out the perceptron
 * algorithm. 
 * @author Krina Nagar
 *
 */
public class Perceptron {
	
	private ArrayList<Image> images = new ArrayList<Image>(); //store all images
	private Feature[] features = new Feature[50]; //store 50 random features
	private double[]weight = new double[50]; //store 50 weights
	
	/**
	 * Scans the file and turns each line into a image with corresponding attributes. 
	 */
	private void loadFile() {
		boolean[][] image = null;
		int rows;
		int cols;
		String category;
		boolean classLabel = true;
		try {
			java.util.regex.Pattern bit = java.util.regex.Pattern.compile("[01]");
			Scanner sc = new Scanner(new File(FileDialog.open()));
			while(sc.hasNextLine()){
				if(!sc.next().equals("P1")){
					System.out.println("Not PBM File");
				}
					category = sc.next().substring(1);
					rows = sc.nextInt();
					cols = sc.nextInt();
					image = new boolean[rows][cols];
					
					for (int i=0; i<rows; i++){
						for (int c=0; c<cols; c++){
							image[i][c] = (sc.findWithinHorizon(bit,0).equals("1"));
						}
					}
					
					int[] imageFeatureValues = new int[50];
					for(int i = 0; i < 50; i++){
						imageFeatureValues[i] = features[i].featureValue(image);
					}

					images.add(new Image(category, imageFeatureValues));
					
					for(int i = 0; i < weight.length; i++){
						weight[i] = new Random().nextDouble();
					}
			}
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
	} 
	
	/**
	 * Generates one dummy feature and 49 random features and adds them 
	 * to the features array.
	 */
	private void initialiseFeatures(){
		features[0] = new Dummy();
		System.out.println("Feature "+ (1) + "= "+ Arrays.toString(features[0].getRow()) + Arrays.toString(features[0].getCol()) + Arrays.toString(features[0].getSign()));
		for(int i = 1; i < 50; i++){
			features[i] = new Feature();
			System.out.println("Feature "+ (i+1) + "= "+ Arrays.toString(features[i].getRow()) + Arrays.toString(features[i].getCol()) + Arrays.toString(features[i].getSign()));
		}
	}
	
	/**
	 * Performs the perceptron algorithm 
	 */
	private void classify() {
		int i = 0;
		int hits = 0;
		int threshold = 1000;
		while(i <= threshold){
			int correct =0;
			for (Image img : images) {
				double value = -1;
				for(int z = 0; z < 50; z++){
					value += weight[z] * img.getFeatureValues()[z];
				}
					//+ve and wrong
	                if(value >0 && (img.getCategory().equals("other"))){
	                	for(int a = 0; a < 50;a++){
	                		weight[a] -= 0.2 * img.getFeatureValues()[a];//update weight
	                	}
	                }
	                //-ve and wrong
	                else if (value<=0 && (img.getCategory().equals("Yes"))){
	                	for(int a = 0; a < 50;a++){
		                	weight[a] += 0.2 * img.getFeatureValues()[a]; //update weight
	                	}
                	}
	                else{
	                	correct++;
	                }
			}
			//check if convergence is reached or not
			if(correct >= images.size()){
				System.out.println("Convergence Reached");
				break;
			} i++;
		}
			System.out.println("Iterations : " + i);
			//calculate the number of hits
			for (Image image : images){
				double value = -1;
				for(int z = 0; z < 50; z++){
					value += weight[z] * image.getFeatureValues()[z];
				}

				if (value >0 && (image.getCategory().equals("Yes")) || value<=0 && (image.getCategory().equals("other"))){
					hits++;
				}
			}
			System.out.println("Weights: " + Arrays.toString(weight) );
			System.out.printf("Correctly guessed : %d/%d", hits, images.size() );
		
	}

	
	public static void main(String[] args){
		Perceptron p = new Perceptron();
		p.initialiseFeatures();
		p.loadFile();
		p.classify();
		System.exit(0);
	}

}
