package dept.Books;

public class Books {
    private String isbn;
    private String name;
    private String author;
    private boolean issued;
    
  
    public Books() {
    }


    public Books(String isbn) {
        this.isbn = isbn;
    }

    
    public Books(String isbn, String name, String author) {
        this.isbn = isbn;
        this.name = name;
        this.author = author;
        System.out.println(this.isbn+this.name+this.author);
    }

    public String getIsbn() {
        return isbn;
    }
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    
    public boolean isIssued(){
        return this.issued;
    }
    
    public void setIssued(boolean issue){
        this.issued = issue;
    }

    @Override
    public String toString() {
        return "Books [author=" + author + ", isbn=" + isbn + ", name=" + name + "issued="+ issued + "]";
    }
    
}
