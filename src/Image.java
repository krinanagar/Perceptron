/**
 * Image class to store the images category (yes or other) and its image
 * @author Krina Nagar
 *
 */
public class Image {
	private String category;
	private int[] featureValues;

	public Image(String category, int[] featureValues) {
		this.category = category;
		this.featureValues = featureValues;
	}

	public String getCategory() {
		return category;
	}

	public int[] getFeatureValues() {
		return featureValues;
	}

	
}
