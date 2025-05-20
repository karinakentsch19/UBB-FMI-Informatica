public class TaskContainerFactory implements Factory{

    private static final TaskContainerFactory instance = new TaskContainerFactory();
    private TaskContainerFactory(){};

    public static TaskContainerFactory getInstance() {
        return instance;
    }

    @Override
    public Container createContainer(Strategy strategy) {
        return switch (strategy){
            //TODO: replace with QueueContainer
            case FIFO ->  new StackContainer();
            case LIFO -> new StackContainer();
        };
    }
}
