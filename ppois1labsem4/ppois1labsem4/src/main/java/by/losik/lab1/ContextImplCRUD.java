package by.losik.lab1;

import java.util.Scanner;

public class ContextImplCRUD extends EntityCRUD{
    private final Serializer serializer = new Serializer(contextImpl);
    public ContextImplCRUD(ContextImpl context, Scanner scanner) {
        super(context, scanner);
    }

    @Override
    public void create() {
        this.contextImpl = new ContextImpl();
        return;
    }

    @Override
    public void read() {
        try {
            serializer.loadState();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete() {
        create();
        System.out.println("Successfully destroyed!");
        update();
    }

    public void update() {
        serializer.saveState();
    }
}
