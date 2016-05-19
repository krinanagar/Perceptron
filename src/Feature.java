/**
 * Feature class to store attributes of the feature and calculate its feature value
 * @author Krina Nagar
 *
 */
public class Feature {
	int[] row;
	int[] col;
	boolean[] sign;
	double weight;
	
	public Feature(){
		this.row = new int[4];
		this.col = new int[4];
		this.sign = new boolean[4];
		weight = new java.util.Random().nextDouble();
		
		for(int k = 0; k < 4; k++){
			boolean h = new java.util.Random().nextBoolean();
			int i = new java.util.Random().nextInt(10);
			int j = new java.util.Random().nextInt(10);


			sign[k] = h;
			row[k] = i;
			col[k] = j;
		}
	}
	
	public int featureValue(boolean[][] image){
		int sum=0;
		for(int i=0; i < 4; i++)
			if (image[row[i]] [col[i]]==sign[i]) sum++;
		return (sum>=3)?1:0;
	}
	
	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight){
		this.weight = weight;
	}

	public int[] getRow() {
		return row;
	}

	public int[] getCol() {
		return col;
	}

	public boolean[] getSign() {
		return sign;
	}
	
	

}
