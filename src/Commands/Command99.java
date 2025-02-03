package Commands;

import Managers.ManagerFacade;

public class Command99 implements Command{
    private final ManagerFacade mFacade;

    public Command99(ManagerFacade mFacade) {
        this.mFacade = ManagerFacade.getInstance();
    }

    @Override
    public void execute() {
        mFacade.case99();
    }
}
