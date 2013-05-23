package security.module;

public class SamplePrincipal implements java.security.Principal,
		java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	String name = null;

	public SamplePrincipal(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	public String toString() {
		return "ce principal a ce nom " + name;
	}

	public boolean equals(Object o) {
		if (o == null)
			return false;

		if (this == o)
			return true;

		if (!(o instanceof SamplePrincipal))
			return false;
		SamplePrincipal that = (SamplePrincipal) o;

		if (this.getName().equals(that.getName()))
			return true;
		return false;
	}

	public int hashCode() {
		return name.hashCode();
	}
}
