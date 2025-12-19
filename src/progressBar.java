public class progressBar {

    private final int total;

    private final int ratio;

    private boolean visibleProgress = false;

    private int current = 0;

    private int percentage = 0;

    progressBar(int total, int ratio){
        this.total = total;
        this.ratio = ratio;
    }

    void progress(){
        current++;
        int curr_percentage = (current * 100) / total;
        if (curr_percentage != percentage) {
            percentage = curr_percentage;
            visibleProgress = true;
        }
    }

    public boolean isVisibleProgress() {
        return visibleProgress;
    }

    @Override
    public String toString() {
        visibleProgress = false;
        int progress = percentage / ratio;
        return "[" + "#".repeat(progress) + " ".repeat((100 / ratio) - progress) + "] " + percentage + "%";
    }
}