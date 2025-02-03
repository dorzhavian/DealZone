package Commands;

import Managers.ManagerFacade;

public class Command101 implements Command{
    private final ManagerFacade mFacade;

    public Command101(ManagerFacade managerFacade) {
        this.mFacade = managerFacade;
    }

    @Override
    public void execute() {
        mFacade.case101();
    }
}
