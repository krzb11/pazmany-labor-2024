<template>
    <section class="section">
        <div class="columns is-centered">
            <div class="column is-4">
                <p class="title is-3 mb-3">Login</p>
                <p class="subtitle is-6">The unlimited fun is just a click away.</p>

                <login-form />
            </div>
        </div>
    </section>
</template>

<script setup>
    import * as Session from '../session.js';
    import LoginForm from '../components/LoginForm.vue';
    import router from '@/router/index.js';

    if (Session.hasActiveSession()) {
        router.push('/');
    }

</script>