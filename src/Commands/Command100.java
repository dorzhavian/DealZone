package Commands;

import Managers.ManagerFacade;

public class Command100 implements Command{
    private final ManagerFacade mFacade;

    public Command100(ManagerFacade mFacade) {
        this.mFacade = ManagerFacade.getInstance();
    }

    @Override
    public void execute() {
        mFacade.case100();
    }
}
