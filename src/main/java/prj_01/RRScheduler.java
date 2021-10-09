package prj_01;

// Ián G. Colón ian.colon6@upr.edu

/**
 *  THIS PROJECT WAS COMPILED AND EXECUTED IN VSCODE
 * 
 * DUE TO DIFFICULTIES WITH THE PROVIDED METHOD OF BUILD OTHER MEASURES WERE USED.
 * THEY ARE THE FOLLOWING:
 * 
 * 1. INSTALLED GRADLE TASKS AND GRADLE LANGUAGE SUPPORT EXTENSIONS
 * 
 * 2. OPENED TERMINAL WINDOW -> CONFIGURE TASKS... -> java: exportjar: Project_1
 * 
 * 3. CLOSED THE CREATED .json FILE
 * 
 * 4. OPENED TERMINAL WINDOW -> RUN BUILD TASK... 
 * 
 * 5. OPENED RUN WINDOW -> RUN WITHOUT DEBUGGING
 * 
 * TO RUN PART 2 THE project_step VARIABLE WAS MANUALLY CHANGED TO 2, THUS TO RUN PART 1 IT HAD TO BE MANUALLY CHANGED BACK TO 1
 */

public class RRScheduler {
    public static void main(String[] args){
        int termination_limit = 100;
        int no_threads = 5;
        int project_step = 2;
        for (int i=0; i<args.length; i++) {
            if (args[i].equals("-t") || args[i].equals("--termination")) {
                termination_limit = Integer.valueOf(args[++i]);
            }
            else if (args[i].equals("-p") || args[i].equals("--processes")) {
                no_threads = Integer.valueOf(args[++i]);
            }
            else if (args[i].equals("-s") || args[i].equals("--prjstep")) {
                project_step = Integer.valueOf(args[++i]);
                if (project_step!=1 && project_step!=2) {
                    System.out.println("Project Step value is 1 or 2 (" + project_step + " given).");
                    System.exit(1);
                }
            }
        }

        System.out.println("Starting Program...");


        RoundRobinCLL roundRobine = null;
        if (project_step==2) {
            roundRobine =  new RoundRobinCLL(12, termination_limit);
        }

        ThreadRunnable rrRunnable = new ThreadRunnable(roundRobine);
        Threads threads = new Threads(no_threads, rrRunnable);

        for (int i=0; i<threads.threads.size(); i++) {
            threads.threads.get(i).start();
        }

        if (roundRobine!=null) roundRobine.findFilledSlot() ;

        System.out.println("Main Finished ... Bye Bye");

        if (roundRobine!=null) roundRobine.stopLoop = true;

    }
}
