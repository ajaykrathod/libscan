package dept;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;
import static com.mongodb.client.model.Sorts.descending;



import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import dept.Books.Books;
import dept.InOut.InOut;
import dept.RetBorrow.RetBorrow;
import dept.Staff.Staff;
import dept.student.Student;
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
import java.text.ParseException;

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
            pass = URLEncoder.encode("ajayrathod@12",StandardCharsets.UTF_8.toString());
            uri = "mongodb://localhost:27017";
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
    public void setUserDatabase(){
        mongoDatabase = mongoClient.getDatabase("libscan").withCodecRegistry(pojoCodecRegistry);
		studentCollection = mongoDatabase.getCollection("student",Student.class);
    }

    // book database intitialisation
    public void setBooksDatabase(){
        mongoDatabase = mongoClient.getDatabase("libscan").withCodecRegistry(pojoCodecRegistry);
		booksCollection = mongoDatabase.getCollection("book",Books.class);
    }

    // staff database intitialisation
    public void setStaffDatabase(){
        mongoDatabase = mongoClient.getDatabase("libscan").withCodecRegistry(pojoCodecRegistry);
		staffCollection = mongoDatabase.getCollection("staff",Staff.class);
    }

    // staff database intitialisation
    public void setInOutDatabase(){
        mongoDatabase = mongoClient.getDatabase("libscan").withCodecRegistry(pojoCodecRegistry);
		inoutCollection = mongoDatabase.getCollection("inout",InOut.class);
    }

    // staff database intitialisation
    public void setRetBorrowDatabase(){
        mongoDatabase = mongoClient.getDatabase("libscan").withCodecRegistry(pojoCodecRegistry);
		retborrowCollection = mongoDatabase.getCollection("retborrow",RetBorrow.class);
    }
    
    // student database operation
    public String addStudent(Student user){
        if(isStudentExists(user)){
            return "User Already Exists";
        }
        else{
            InsertOneResult insertOneResult = studentCollection.insertOne(user);
            return insertOneResult.getInsertedId().toString();
        }
    }

    public boolean deleteStudent(String prn){
        DeleteResult deleteResult = studentCollection.deleteOne(Filters.eq("prn", prn));
		return deleteResult.wasAcknowledged();
    }

    public void updateIsInStudent(Student inOut,boolean is){
        System.out.println(is);
        if(is) {
            UpdateResult updateResult = studentCollection.updateOne(Filters.eq("prn",inOut.getPrn()), Updates.combine(Updates.set("isIn", false)));
            System.out.print(updateResult.getModifiedCount());
        }
        else {
            UpdateResult updateResult = studentCollection.updateOne(Filters.eq("prn",inOut.getPrn()), Updates.combine(Updates.set("isIn", false)));
            System.out.print(updateResult.getModifiedCount());
        }
    }
        
    public Long updateStudent(Student student) {
		UpdateResult updateResult = studentCollection.updateOne(
            Filters.eq("prn",student.getPrn()),
            Updates.combine(
                Updates.set("name", student.getName()),
                Updates.set("books", student.getBooks()),
                Updates.set("mail", student.getMail()),
                Updates.set("contact",student.getContact()),
                Updates.set("in", student.isIn())
            )
        );
		return updateResult.getModifiedCount();
	}

    public boolean isStudentExists(Student student){
        if(studentCollection.find(Filters.eq("prn",student.getPrn())).first() != null){
            return true;
        }
        return false;
    }


    public String getStudent(String prn) {
        FindIterable<Student> findIterable = studentCollection.find(Filters.eq("prn",prn));
		MongoCursor<Student> cursor = findIterable.cursor();
	    while(cursor.hasNext()){
			Student stud = cursor.next();
			System.out.println(stud.toString());
                        return stud.toString();
		}
            return "";
    } 
    
    public Student getStudentObj(String prn) {
        Student stud = new Student();
        FindIterable<Student> findIterable = studentCollection.find(Filters.eq("prn",prn));
		MongoCursor<Student> cursor = findIterable.cursor();
	    while(cursor.hasNext()){
                stud = cursor.next();
                return stud;
	    }
            return stud;
    } 

    public MongoCursor<Student> getAllStudents(){
        FindIterable<Student> findIterable = studentCollection.find();
		MongoCursor<Student> cursor = findIterable.cursor();
	    return cursor;
    }

    // books database operation
    public String addBook(Books books){
        InsertOneResult insertOneResult = booksCollection.insertOne(books);
		return insertOneResult.getInsertedId().toString();
    }
    
    public boolean deleteBook(String barcode){
        DeleteResult deleteResult = booksCollection.deleteOne(Filters.eq("isbn", barcode));
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
    public boolean isBookIssued(String isbn){
        Books book = booksCollection.find(Filters.eq("isbn",isbn)).first();
        if(book.isIssued()){
            return true;
        }
        else return false;
    }
    public Books getBook(String barcode) {
        return booksCollection.find(Filters.eq("isbn",barcode)).first();
        // FindIterable<Books> findIterable = booksCollection.find(Filters.eq("isbn",books.getIsbn()));
		// MongoCursor<Books> cursor = findIterable.cursor();
	    // while(cursor.hasNext()){
		// 	Books book = cursor.next();
		// 	System.out.println(book.toString());
		// }
    }

    public MongoCursor<Books> getAllBooks() {
        FindIterable<Books> findIterable = booksCollection.find();
		MongoCursor<Books> cursor = findIterable.cursor();
	    return cursor;
    }
    
    public boolean isBookExists(Books books){
        if(booksCollection.find(Filters.eq("isbn",books.getIsbn())).first() != null) return true;
        else return false;
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
        if(student.isIn()){
            return true;
        }
        else {
            return false;
        }
    }

    public void decideInOut(Student student){
        Student stud = studentCollection.find(Filters.eq("prn", student.getPrn())).first();
        System.out.println(stud.toString());
        Boolean is = stud.isIn();
        System.out.println(is);
            if(is){
                InOut inOut = new InOut(stud.getPrn(), "out");
                insertInOut(inOut);
            }
            else{
                InOut inOut = new InOut(stud.getPrn(), "in");
                insertInOut(inOut);
            }
//            stud.setIn(!is);
            updateIsInStudent(stud,!is);
//            updateStudent(stud);
    }
    
    public InOut sortedInOut(String prn){
        return inoutCollection.find(Filters.eq("prn",prn)).sort(descending("date")).first();
//        MongoCursor<InOut> cursor = iterable.cursor();
//        while(cursor.hasNext()){
//            System.out.println(cursor.next().toString());
//        }
    }

    public MongoCursor<InOut> getStudentInOutRecord(Student student){
        FindIterable<InOut> findIterable = inoutCollection.find(Filters.eq("prn",student.getPrn()));
		MongoCursor<InOut> cursor = findIterable.cursor();
	    return cursor;
    }

    public void setOutTime(Student student,String date){
        InOut inOut = new InOut(student.getPrn(), "out");
        inOut.setDate(date);
        inOut.setTime("18:00:00");
        InsertOneResult insertOneResult = inoutCollection.insertOne(inOut);
    }

    public void setAllOut(){
        FindIterable<Student> findIterable = studentCollection.find(Filters.eq("isIn", true));
		MongoCursor<Student> cursor = findIterable.cursor();
	    while(cursor.hasNext()){
			Student stud = cursor.next();
            stud.setIn(false);
            updateStudent(stud);
            
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            Date dt = new Date();
            setOutTime(stud,dateFormat.format(dt));
        }
    }

    public String insertInOut(InOut inout){
//        updateIsInStudent(inout);
        InsertOneResult insertOneResult = inoutCollection.insertOne(inout);
		return insertOneResult.getInsertedId().toString();
    }

    // retborrow database operation
    public void decideRetBorrow(Student student,Books book){
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

    public void insertRetBorrow(RetBorrow retBorrow){
        InsertOneResult insertOneResult = retborrowCollection.insertOne(retBorrow);
    }

    public MongoCursor<RetBorrow> getStudentRetBorrowRecord(Student student){
        FindIterable<RetBorrow> findIterable = retborrowCollection.find(Filters.eq("prn",student.getPrn()));
		MongoCursor<RetBorrow> cursor = findIterable.cursor();
                return cursor;
    }
    
    public MongoCursor<RetBorrow> getBookRetBorrowRecord(String isbn){
        FindIterable<RetBorrow> iterable = retborrowCollection.find(Filters.eq("isbn",isbn));
        MongoCursor<RetBorrow> cursor = iterable.cursor();
        return cursor;
    }
    
    public Vector<RetBorrow> getFilteredRetBorrowRecord(String filter){
        SimpleDateFormat obj = new SimpleDateFormat("YYYY-MM-DD");   
        Vector<RetBorrow> retBorrows = new Vector<>();
        // Use parse method to get date object of both dates
        FindIterable<RetBorrow> iterable = retborrowCollection.find();
        MongoCursor<RetBorrow> cursor= iterable.cursor();
        Date date1 = new Date();
        while(cursor.hasNext()){
            RetBorrow ret = cursor.next();
            
            Date date2 = new Date(ret.getDate());
            // Calucalte time difference in milliseconds
            long time_difference = date2.getTime() - date1.getTime();
            // Calucalte time difference in days
            long days_difference = (time_difference / (1000*60*60*24)) % 365;
            // Calucalte time difference in years
            long years_difference = (time_difference / (1000l*60*60*24*365));
            // Calucalte time difference in seconds
            long seconds_difference = (time_difference / 1000)% 60;
            // Calucalte time difference in minutes
            long minutes_difference = (time_difference / (1000*60)) % 60;
            
            // Calucalte time difference in hours
            long hours_difference = (time_difference / (1000*60*60)) % 24;
            // Show difference in years, in days, hours, minutes, and seconds
            if(filter.equals("Today")){
                if(date1.getDate() == date2.getDate() && date1.getMonth() == date2.getMonth() && date1.getYear() == date2.getYear()){
                    retBorrows.add(ret);
                }
            }
            else if(filter.equals("7 Days")){
                if(days_difference <= 7){
                    retBorrows.add(ret);
                }
            }
            else if(filter.equals("1 month")){
                
            }
            else if(filter.equals("1 Year")){
                if(years_difference <= 1){
                    retBorrows.add(ret);
                }
            }
        }
        return retBorrows;   
    }
}
