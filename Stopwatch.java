/***********************************************************
 * Created by:   Adolfo Herrera                                      
 *
 * Date:     Jan 25, 2019             
 *
 * Purpose:  Simple Stopwatch Class
 * This class will allow you to implement a standard StopWatch
 * with the basic features such as START, STOP, and RESET
 * This class is implemented as runnable so a thread is required!
 *
 ********************************************************/

class Stopwatch implements Runnable  {

    /**
     * Two forms of saving the time are required mainly for remembering what the last time was
     * Exit variable is for thread usage
     */

    private volatile boolean exit = true;

    private static int hours;
    private static int min;
    private static int seconds;

    private static int currentHours =0;
    private static int currentMin = 0;
    private static int currentSeconds = 0;

    /**
     * Main function that will start the time.
     * Once the stop function is pressed this thread will sleep
     */

    public void run() {
        while(true) {
            if (!exit) {

                for (hours = currentHours; hours < 24; hours++) {
                    if (exit) {
                        hours--;
                        currentHours = hours;
                        break;
                    }
                    // 60 mins in an hours
                    for (min = currentMin; min < 60; min++) {

                        // 60 secs in a min
                        if (exit) {
                            min--;
                            currentMin = min;
                            break;
                        }
                        for (seconds = currentSeconds; seconds < 60; seconds++) {

                            if (exit) {
                                currentSeconds = seconds;
                                break;
                            }
                            System.out.println(hours + ":" + min + ":" + seconds);
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException ex) {
                                Thread.currentThread().interrupt();
                            }
                        }
                    }
                }
            }
            else
            {
                try {
                    System.out.println("Entered Here");
                    synchronized (this) {
                        wait();
                    }


                } catch(InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    /**
     *  Stops timer and remembers time at which it stopped
     *  to set time once start is clicked again
     */

    public void stopMyTimer() {
        if (exit) {
            synchronized (this) {
                notify();
            }
        }
        currentHours = hours;
        currentMin = min;
        currentSeconds = seconds;
        exit = true;
    }

    /**
     * Will start time by notifying the sleeping thread in start function
     */

    public void startMyTimer() {
        exit = false;
        synchronized (this) {
            notify();
        }
    }

    /**
     * Returns what the current time is at
     */
    public String timeStamp() {
        return hours + ":" + min + ":" + seconds;
    }

    /**
     * Resets all variables back to zero indicating the timer has been reset
     */
    public void resetTimer() {
        hours = 0;
        min = 0;
        seconds = 0;
        currentSeconds = 0;
        currentMin = 0;
        currentHours = 0;
    }
}
