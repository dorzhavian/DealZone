package Commands;

import Managers.ManagerFacade;
import java.util.*;

public class MenuCommand implements Command{
    private final ManagerFacade mFacade;

    public MenuCommand(ManagerFacade managerFacade) {
        this.mFacade = managerFacade;
    }

    @Override
    public void execute(int choice) {
        switch (choice)
        {
            case 99:
                mFacade.case99();
                break;

            case 100:
                mFacade.case100();
                break;

            case 101:
                mFacade.case101();
                break;

            case 102:
                mFacade.case102();
                break;

            case 103:
                mFacade.case103();
                break;

            default:
                break;
        }

    }
}
