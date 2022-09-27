package com.dept;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import com.dept.Books.Books;
import com.dept.InOut.InOut;
import com.dept.RetBorrow.RetBorrow;
import com.dept.Staff.Staff;
import com.dept.student.Student;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

public class DBConnection {

    String pass,uri;
    DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
	String form = format.format(new Date());
    ConnectionString connectionString;
	MongoClientSettings mongoClientSettings;
	MongoClient mongoClient;
	MongoDatabase mongoDatabase;
	CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
    CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));

    MongoCollection<Student> studentCollection;
    MongoCollection<Books> booksCollection;
    MongoCollection<Staff> staffCollection;
    MongoCollection<InOut> inoutCollection;
    MongoCollection<RetBorrow> retborrowCollection;

    // initialisation
    public DBConnection() {
        try {
            pass = URLEncoder.encode("password",StandardCharsets.UTF_8.toString());
            uri = "mongodb+srv://username:"+pass +"@realmcluster.th5q0.mongodb.net/?retryWrites=true&w=majority";
        } catch (Exception e) {
            e.printStackTrace();
        }
        connectionString = new ConnectionString(uri);
		mongoClientSettings = MongoClientSettings.builder()
				.applyConnectionString(connectionString)
				.serverApi(ServerApi.builder()
						.version(ServerApiVersion.V1)
						.build())
				.build();
		mongoClient = MongoClients.create(mongoClientSettings);
    }

    // student database intitialisation
    void setUserDatabase(){
        mongoDatabase = mongoClient.getDatabase("libscan").withCodecRegistry(pojoCodecRegistry);
		studentCollection = mongoDatabase.getCollection("student",Student.class);
    }

    // book database intitialisation
    void setBooksDatabase(){
        mongoDatabase = mongoClient.getDatabase("libscan").withCodecRegistry(pojoCodecRegistry);
		booksCollection = mongoDatabase.getCollection("book",Books.class);
    }

    // staff database intitialisation
    void setStaffDatabase(){
        mongoDatabase = mongoClient.getDatabase("libscan").withCodecRegistry(pojoCodecRegistry);
		staffCollection = mongoDatabase.getCollection("staff",Staff.class);
    }

    // staff database intitialisation
    void setInOutDatabase(){
        mongoDatabase = mongoClient.getDatabase("libscan").withCodecRegistry(pojoCodecRegistry);
		inoutCollection = mongoDatabase.getCollection("inout",InOut.class);
    }

    // staff database intitialisation
    void setRetBorrowDatabase(){
        mongoDatabase = mongoClient.getDatabase("libscan").withCodecRegistry(pojoCodecRegistry);
		retborrowCollection = mongoDatabase.getCollection("retborrow",RetBorrow.class);
    }
    
    // student database operation
    String addStudent(Student user){
        if(isStudentExists(user)){
            return "User Already Exists";
        }
        else{
            InsertOneResult insertOneResult = studentCollection.insertOne(user);
            return insertOneResult.getInsertedId().toString();
        }
    }

    boolean deleteStudent(Student student){
        DeleteResult deleteResult = studentCollection.deleteOne(Filters.eq("prn", student.getPrn()));
		return deleteResult.wasAcknowledged();
    }

    public Long updateStudent(Student student) {
		UpdateResult updateResult = studentCollection.updateOne(
            Filters.eq("prn",student.getPrn()),
            Updates.combine(
                Updates.set("name", student.getName()),
                Updates.set("books", student.getBooks()),
                Updates.set("mail", student.getMail()),
                Updates.set("isIn", student.isIn())
            )
        );
		return updateResult.getModifiedCount();
	}

    boolean isStudentExists(Student student){
        if(studentCollection.find(Filters.eq("prn",student.getPrn())).first() != null){
            return true;
        }
        return false;
    }


    public void getStudent(Student student) {
        FindIterable<Student> findIterable = studentCollection.find(Filters.eq("prn",student.getPrn()));
		MongoCursor<Student> cursor = findIterable.cursor();
	    while(cursor.hasNext()){
			Student stud = cursor.next();
			System.out.println(stud.toString());
		}
    }

    public void getAllStudents(){
        FindIterable<Student> findIterable = studentCollection.find();
		MongoCursor<Student> cursor = findIterable.cursor();
	    while(cursor.hasNext()){
			Student stud = cursor.next();
			System.out.println(stud.toString());
		}
    }

    // books database operation
    String addBook(Books books){
        InsertOneResult insertOneResult = booksCollection.insertOne(books);
		return insertOneResult.getInsertedId().toString();
    }
    
    boolean deleteBook(Books books){
        DeleteResult deleteResult = booksCollection.deleteOne(Filters.eq("prn", books.getIsbn()));
		return deleteResult.wasAcknowledged();
    }

    public Long updateBook(Books books) {
		UpdateResult updateResult = booksCollection.updateOne(
            Filters.eq("isbn",books.getIsbn()),
            Updates.combine(
                Updates.set("name", books.getName()),
                Updates.set("name", books.getAuthor())
            )
        );
		return updateResult.getModifiedCount();
	}

    public Books getBook(Books books) {
        return booksCollection.find(Filters.eq("isbn",books.getIsbn())).first();
        // FindIterable<Books> findIterable = booksCollection.find(Filters.eq("isbn",books.getIsbn()));
		// MongoCursor<Books> cursor = findIterable.cursor();
	    // while(cursor.hasNext()){
		// 	Books book = cursor.next();
		// 	System.out.println(book.toString());
		// }
    }

    public void getAllBooks() {
        FindIterable<Books> findIterable = booksCollection.find();
		MongoCursor<Books> cursor = findIterable.cursor();
	    while(cursor.hasNext()){
			Books book = cursor.next();
			System.out.println(book.toString());
		}
    }

    // staff database operation
    String addStaff(Staff staff){
        InsertOneResult insertOneResult = staffCollection.insertOne(staff);
		return insertOneResult.getInsertedId().toString();
    }
    
    boolean deleteStaff(Staff staff){
        DeleteResult deleteResult = staffCollection.deleteOne(Filters.eq("id", staff.getId()));
		return deleteResult.wasAcknowledged();
    }

    public Long updateStaff(Staff staff) {
		UpdateResult updateResult = staffCollection.updateOne(Filters.eq("id",staff.getId()), Updates.push("name", staff.getName()));
		return updateResult.getModifiedCount();
	}

    public void getStaff(Staff staff) {
        FindIterable<Staff> findIterable = staffCollection.find(Filters.eq("id",staff.getId()));
		MongoCursor<Staff> cursor = findIterable.cursor();
	    while(cursor.hasNext()){
			Staff stf = cursor.next();
			System.out.println(stf.toString());
		}
    }

    // inout database operation
    boolean isIn(Student student){
        if(student.isIn() == true) return true;
        else return false;
    }

    void decideInOut(Student student){
        Student stud = studentCollection.find(Filters.eq("prn", student.getPrn())).first();
            if(isIn(stud) == true){
                stud.setIn(false);
                InOut inOut = new InOut(stud.getPrn(), "out");
                insertInOut(inOut);
            }
            else{
                stud.setIn(true);
                InOut inOut = new InOut(stud.getPrn(), "in");
                insertInOut(inOut);
            }
            updateStudent(stud);
    }

    void getStudentInOutRecord(Student student){
        FindIterable<InOut> findIterable = inoutCollection.find(Filters.eq("prn",student.getPrn()));
		MongoCursor<InOut> cursor = findIterable.cursor();
	    while(cursor.hasNext()){
			InOut inOut = cursor.next();
			System.out.println(inOut.toString());
		}
    }

    void setOutTime(Student student){
        InOut inOut = new InOut(student.getPrn(), "out");
        inOut.setTime("18:00:00");
        InsertOneResult insertOneResult = inoutCollection.insertOne(inOut);
    }

    void setAllOut(){
        FindIterable<Student> findIterable = studentCollection.find(Filters.eq("isIn", true));
		MongoCursor<Student> cursor = findIterable.cursor();
	    while(cursor.hasNext()){
			Student stud = cursor.next();
            stud.setIn(false);
            updateStudent(stud);
            setOutTime(stud);
		}
    }

    String insertInOut(InOut inout){
        InsertOneResult insertOneResult = inoutCollection.insertOne(inout);
		return insertOneResult.getInsertedId().toString();
    }

    // retborrow database operation
    void decideRetBorrow(Student student,Books book){
        Student stud = studentCollection.find(Filters.eq("prn", student.getPrn())).first();
            Vector<String> books = stud.getBooks();
            if(isBorrowed(stud, book)){
                books.remove((Object)book.getIsbn());

                RetBorrow retBorrow = new RetBorrow(stud.getPrn(),books,"return");
                insertRetBorrow(retBorrow);
            }
            else {
                RetBorrow retBorrow = new RetBorrow(stud.getPrn(),books, "borrow");
                books.add(book.getIsbn());
                insertRetBorrow(retBorrow);
            }
            stud.setBooks(books);
            updateStudent(stud);
    }

    public boolean isBorrowed(Student student,Books book){
        Vector<String> books = student.getBooks();
        if(books.contains(book.getIsbn())){
            System.out.println(books.contains(book.getIsbn()));
            return true;
        }
        else {
            System.out.println("Not Present");
            return false;
        }
    }

    void insertRetBorrow(RetBorrow retBorrow){
        InsertOneResult insertOneResult = retborrowCollection.insertOne(retBorrow);
    }

    void getStudentRetBorrowRecord(Student student){
        FindIterable<RetBorrow> findIterable = retborrowCollection.find(Filters.eq("prn",student.getPrn()));
		MongoCursor<RetBorrow> cursor = findIterable.cursor();
	    while(cursor.hasNext()){
			RetBorrow retBorrow = cursor.next();
			System.out.println(retBorrow.toString());
		}
    }
}
