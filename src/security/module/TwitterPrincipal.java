package security.module;

public class TwitterPrincipal implements java.security.Principal,
		java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	String name = null;

	public TwitterPrincipal(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
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

		if (!(o instanceof TwitterPrincipal))
			return false;
		TwitterPrincipal that = (TwitterPrincipal) o;

		if (this.getName().equals(that.getName()))
			return true;
		return false;
	}

	public int hashCode() {
		return name.hashCode();
	}
}
