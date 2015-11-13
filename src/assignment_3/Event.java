package assignment_3;

/**
 * Created by Yuxibro on 15-11-09.
 */

public class Event {
    int ID;
    double time;
    EventType eventType;
    int eventSubject;       // 1 - Batch, 2 - Customer,
    int subjectID;

    public Event(int ID, double time, EventType e , int subject,int subjectID ){
        this.ID = ID;
        this.time = time;
        this.eventType = e;
        this.eventSubject = subject;
        this.subjectID = subjectID;
        //System.out.println("Making event for: "+this.eventSubject+"-"+ subjectID);
    }
    public void writeState(){
        System.out.println("EventID: "+this.ID+"\ttime:"+this.time+"\tEventType: "+this.eventType+"\tsubject: "+this.eventSubject+"\tID:"+this.subjectID);
    }

}
