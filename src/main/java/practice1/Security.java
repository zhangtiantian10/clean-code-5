package practice1;

import com.google.common.collect.ImmutableList;

public class Security {

    private SecurityChecker securityChecker;

    public Security(SecurityChecker checker) {
        this.securityChecker = checker;
    }

    public boolean hasAccess(User user, Permission permission, ImmutableList<Permission> permissions) {

        boolean isAccess = false;

        boolean checkPermission = this.securityChecker.checkPermission(user, permission);
        boolean containsPermission = permissions.contains(permission);
        boolean isAdmin = securityChecker.isAdmin();

        if (checkPermission || containsPermission || isAdmin) {
            isAccess = true;
        }

        return isAccess;
    }
}
