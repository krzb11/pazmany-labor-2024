<template>
    <section class="section">
        <div class="columns is-centered">
            <div class="column is-2">
                <button class="button is-loading is-text is-large"></button>
            </div>
        </div>
    </section>
</template>

<script>
import * as Session from '../session.js';
import router from '@/router/index.js';

export default {
  data() {
    return { };
  },
  created() {
    this.logoutUser();
  },
  methods: {
    async logoutUser() {
        try {
            await fetch(process.env.VUE_APP_API_URL+'/logout', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                username: this.username,
                sessionkey: Session.getSessionKey()
            })
            });

            Session.clearSession();
            router.push('/');
        } catch (error) {
            this.error = error.message;
        }
    }
  }
};
</script>