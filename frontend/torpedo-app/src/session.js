export function hasActiveSession() {
    if (!localStorage.getItem("torpedo_session")) {
        return false;
    } else {
        try {
            let session = JSON.parse(localStorage.getItem("torpedo_session"));
            if (Date.parse(session.expire) <= Date.now()) {
                return false;
            } else {
                return true;
            }
        } catch (error) {
            console.log(error);
            return false;
        }
    }
}

export function clearSession() {
    localStorage.removeItem("torpedo_session");
}

export function addSession(session) {
    localStorage.setItem("torpedo_session", session);
}

export function getSessionUsername() {
    if (localStorage.getItem("torpedo_session")) {
        try {
            let session =  JSON.parse(localStorage.getItem("torpedo_session"));
            return session.username;
        } catch (error) {
            console.log(error);
            return('');
        }
    }
}