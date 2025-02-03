package Commands;

import Managers.ManagerFacade;

public class Command102 implements Command{
    private final ManagerFacade mFacade;

    public Command102(ManagerFacade managerFacade) {
        this.mFacade = managerFacade;
    }

    @Override
    public void execute() {
        mFacade.case102();
    }
}
