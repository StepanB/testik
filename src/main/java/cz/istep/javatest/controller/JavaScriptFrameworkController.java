package cz.istep.javatest.controller;

import cz.istep.javatest.data.JavaScriptFramework;
import cz.istep.javatest.repository.JavaScriptFrameworkRepository;
import cz.istep.javatest.rest.FrameworkNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class JavaScriptFrameworkController {

	private final JavaScriptFrameworkRepository repository;

	@Autowired
	public JavaScriptFrameworkController(JavaScriptFrameworkRepository repository) {
		this.repository = repository;
	}

	@GetMapping("/frameworks")
	public Iterable<JavaScriptFramework> frameworks() {
		return repository.findAll();
	}

	@PostMapping("/createFramework")
	public JavaScriptFramework createFramework(@Valid @RequestBody JavaScriptFramework newFramework) {

		return repository.save(newFramework);

	}

	@PutMapping("/editFramework/{id}")
	public JavaScriptFramework editFramework(@Valid @RequestBody JavaScriptFramework newFramework, @PathVariable Long id) {

		return repository.findById(id).map(framework -> {
			framework.setName(newFramework.getName());
			framework.setHypeLevel(newFramework.getHypeLevel());
			framework.setVersion(newFramework.getVersion());

			return repository.save(framework);
		}).orElseGet(() -> {
			newFramework.setId(id);
			return repository.save(newFramework);
		});
	}

	@DeleteMapping("/deleteFramework/{id}")
	public void deleteFramework(@PathVariable Long id) throws FrameworkNotFoundException {
		if (repository.findById(id).isEmpty()) {
			throw new FrameworkNotFoundException(id);
		} else {
			repository.deleteById(id);
		}
	}
}
