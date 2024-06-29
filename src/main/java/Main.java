import java.time.LocalDate;

import entity.Author;
import entity.Book;
import entity.BookBorrowing;
import entity.Category;
import entity.Publisher;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("library-management-system");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            // Author 1: Douglas Noel Adams
            Author douglasAdams = new Author();
            douglasAdams.setName("Douglas Noel Adams");
            douglasAdams.setBirthDate(1952);
            douglasAdams.setCountry("United Kingdom");
            entityManager.persist(douglasAdams);

            // Author 2: Arthur C. Clarke
            Author arthurClarke = new Author();
            arthurClarke.setName("Arthur C. Clarke");
            arthurClarke.setBirthDate(1917);
            arthurClarke.setCountry("United Kingdom");
            entityManager.persist(arthurClarke);

            // Publisher: Pan Books
            Publisher panBooks = new Publisher();
            panBooks.setName("Pan Books");
            panBooks.setEstablishmentYear(1944);
            panBooks.setAddress("London, UK");
            entityManager.persist(panBooks);

            // Publisher: Hutchinson
            Publisher hutchinson = new Publisher();
            hutchinson.setName("Hutchinson");
            hutchinson.setEstablishmentYear(1887);
            hutchinson.setAddress("London, UK");
            entityManager.persist(hutchinson);

            // Category: Science Fiction
            Category scienceFictionCategory = new Category();
            scienceFictionCategory.setName("Science Fiction");
            scienceFictionCategory.setDescription("Science Fiction literature");
            entityManager.persist(scienceFictionCategory);

            // Book 1: Hitchhiker's Guide to the Galaxy
            Book hitchhikersGuide = new Book();
            hitchhikersGuide.setName("Hitchhiker's Guide to the Galaxy");
            hitchhikersGuide.setPublicationYear(1979);
            hitchhikersGuide.setStock(15);
            hitchhikersGuide.setAuthor(douglasAdams);
            hitchhikersGuide.setPublisher(panBooks);
            hitchhikersGuide.getCategories().add(scienceFictionCategory);
            entityManager.persist(hitchhikersGuide);

            // Book 2: 2001: A Space Odyssey
            Book spaceOdyssey = new Book();
            spaceOdyssey.setName("2001: A Space Odyssey");
            spaceOdyssey.setPublicationYear(1968);
            spaceOdyssey.setStock(12);
            spaceOdyssey.setAuthor(arthurClarke);
            spaceOdyssey.setPublisher(hutchinson);
            spaceOdyssey.getCategories().add(scienceFictionCategory);
            entityManager.persist(spaceOdyssey);

            BookBorrowing borrowing1 = new BookBorrowing();
            borrowing1.setBorrowerName("Alice Johnson");
            borrowing1.setBorrowingDate(LocalDate.now());
            borrowing1.setBook(hitchhikersGuide);
            hitchhikersGuide.getBorrowings().add(borrowing1);
            entityManager.persist(borrowing1);

            BookBorrowing borrowing2 = new BookBorrowing();
            borrowing2.setBorrowerName("Bob Smith");
            borrowing2.setBorrowingDate(LocalDate.now());
            borrowing2.setBook(spaceOdyssey);
            spaceOdyssey.getBorrowings().add(borrowing2);
            entityManager.persist(borrowing2);

            transaction.commit();

            Book foundHitchhikersGuide = entityManager.find(Book.class, hitchhikersGuide.getId());
            Book foundSpaceOdyssey = entityManager.find(Book.class, spaceOdyssey.getId());

            // Display information for Hitchhiker's Guide to the Galaxy
            printBooks(foundHitchhikersGuide);

            // Display information for 2001: A Space Odyssey
            printBooks(foundSpaceOdyssey);

        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (entityManager.isOpen()) {
                entityManager.close();
            }
            if (entityManagerFactory.isOpen()) {
                entityManagerFactory.close();
            }
        }
    }

    private static void printBooks(Book foundBook) {
        System.out.println("Found book: " + foundBook.getName());
        System.out.println("Author: " + foundBook.getAuthor().getName());
        System.out.println("Publisher: " + foundBook.getPublisher().getName());

        foundBook.getCategories().forEach(category -> System.out.print(category.getName() + ", "));
        System.out.println("Borrowings: " + foundBook.getBorrowings().size());

        System.out.println("Borrowings details:");
        for (BookBorrowing b : foundBook.getBorrowings()) {
            System.out.println("  Borrower: " + b.getBorrowerName() + ", Date: " + b.getBorrowingDate());
        }
    }
}
