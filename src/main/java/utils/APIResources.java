package utils;

public enum APIResources {
	
	GetAuthAPI("/auth/me"),
	EventAPI("/events"),
	EventAPIById("/events/{id}");
	
	private String resource;
	
	APIResources(String resource) {
		this.resource = resource;
	}
	
	public String getResource() {
		return resource;
	}

}
