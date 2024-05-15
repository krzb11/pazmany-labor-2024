<script>

export default {
  methods: {
    hasActiveSession() {
        if (!localStorage.getItem("torpedo_session")) {
            return false;
        } else {
            let session = localStorage.getItem("torpedo_session").json();
            if (Date.parse(session.expire) <= Date.now()) {
                localStorage.removeItem("torpedo_session");
                return false;
            } else {
                return true;
            }
        }

        return false;
    },
    clearSession() {
        localStorage.removeItem("torpedo_session");
    },
    addSession(session) {
        localStorage.setItem("torpedo_session", session);
    },
    getSessionUsername() {
        if (localStorage.getItem("torpedo_session")) {
            let session = localStorage.getItem("torpedo_session").json();
            return session.username;
        }
        return '';
    }
  }
};
</script>