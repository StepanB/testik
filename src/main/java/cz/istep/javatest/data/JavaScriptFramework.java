package cz.istep.javatest.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
public class JavaScriptFramework {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Size(min = 3, max = 30)
	@NotBlank(message = "Name is mandatory")
	private String name;

	@Size(min = 1, max = 10)
	@NotBlank(message = "Version is mandatory")
	private String version;

	private transient Hype hypeLevel;

	public JavaScriptFramework() {
	}

	public JavaScriptFramework(String name) {
		this.name = name;
	}

	public JavaScriptFramework(Long id, String name, String version) {
		this.id = id;
		this.name = name;
		this.version = version;
	}

	public JavaScriptFramework(Long id, String name, String version, Hype hypeLevel) {
		this.id = id;
		this.name = name;
		this.version = version;
		this.hypeLevel = hypeLevel;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Hype getHypeLevel() {
		return hypeLevel;
	}

	public void setHypeLevel(Hype hypeLevel) {
		this.hypeLevel = hypeLevel;
	}

	@Override
	public String toString() {
		return "JavaScriptFramework{" +
				"id=" + id +
				", name='" + name + '\'' +
				", version='" + version + '\'' +
				", hypeLevel=" + hypeLevel +
				'}';
	}
}
