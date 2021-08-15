package library;

public class results {
    private int id;
    private int result;
    private String status,course,name;

    public String getCourse() {
        return course;
    }

    public int getId() {
        return id;
    }

    public int getResult() {
        return result;
    }

    public String getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }



    public results(int id,String name,int result,String status,String course){
        this.id = id;
        this.result = result;
        this.name = name;
        this.status = status;
        this.course = course;
    }
}
