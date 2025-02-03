package Commands;

import Managers.ManagerFacade;

public class Command103 implements Command{
    private final ManagerFacade mFacade;

    public Command103(ManagerFacade managerFacade) {
        this.mFacade = managerFacade;
    }

    @Override
    public void execute() {
        mFacade.case103();
    }
}
