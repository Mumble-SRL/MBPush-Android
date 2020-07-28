package mumble.mbpush.MBPushData;

public class MBTopic {

    private String topic, title;
    private boolean single = false;

    public MBTopic(String topic, String title, boolean single) {
        this.topic = topic;
        this.title = title;
        this.single = single;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isSingle() {
        return single;
    }

    public void setSingle(boolean single) {
        this.single = single;
    }
}
