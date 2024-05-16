<template>
  <section class="section">
        <div class="columns is-centered">
            <div class="column is-6">
              <figure class="image is-square">
                <img src="../assets/torpedo1_img.webp" />
              </figure>
            </div>
            <div class="column is-3">
                <p class="title is-3 mb-3">
                    Welcome to the Word of Torpedoo!
                </p>
                <p class="subtitle is-6">Join the comminity or log in to see the global leaderboard.</p>

                <div class="container">
                  <div class="buttons">
                        <a class="button is-link" @click="routeRegister()">
                            <strong>Sign up</strong>
                        </a>
                        <a class="button is-light is-outlined" @click="routeLogin()">
                            Log in
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </section>
</template>

<script>
import router from '@/router/index.js';

export default {
    data() {
        return {
            error: ''
        };
    },
    methods: {
        routeLogin() {
            router.push('/login');
        },
        routeRegister() {
            router.push('/register');
        }
    }
}
</script>
