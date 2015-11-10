package assignment_3;

/**
 * Created by Yuxibro on 15-11-09.
 */

public class Event {
    int ID;
    double time;
    EventType eventType;

    public Event(int ID, double time, EventType e){
        this.ID = ID;
        this.time = time;
        this.eventType = e;
    }
    public void writeState(){
        System.out.println("EventID: "+this.ID+"\ttime:"+this.time+"\tEventType: "+this.eventType);
    }

}
