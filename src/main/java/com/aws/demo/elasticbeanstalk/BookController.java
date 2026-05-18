package com.aws.demo.elasticbeanstalk;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/books")
public class BookController {

	private final BookRepository bookRepository;

	public BookController(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	@GetMapping
	public List<Book> getBooks() {
		return bookRepository.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Book> getBook(@PathVariable Long id) {
		return bookRepository.findById(id)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<Book> createBook(@RequestBody Book book) {
		book.setId(null);
		Book savedBook = bookRepository.save(book);

		return ResponseEntity
				.created(URI.create("/api/books/" + savedBook.getId()))
				.body(savedBook);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book book) {
		return bookRepository.findById(id)
				.map(existingBook -> {
					existingBook.setTitle(book.getTitle());
					existingBook.setAuthor(book.getAuthor());
					existingBook.setIsbn(book.getIsbn());
					existingBook.setPublishedYear(book.getPublishedYear());

					return ResponseEntity.ok(bookRepository.save(existingBook));
				})
				.orElse(ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
		if (!bookRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}

		bookRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
