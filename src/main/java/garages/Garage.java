package garages;

public class Garage {

	private String name;

	public Garage(String s) {
		name = s;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (null == name) {
			throw new IllegalArgumentException("name is null");
		}

		this.name = name;
	}

	@Override
	public String toString() {
		return "Garage " + name;
	}

}
