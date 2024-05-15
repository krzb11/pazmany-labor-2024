<template>
    <section class="section">
        <div class="columns is-centered">
            <div class="column is-4">
                <p class="title is-3 mb-3">Sign up</p>
                <p class="subtitle is-6">Join the coolest Torpedoo community now!</p>

                <register-form />
            </div>
        </div>
    </section>
</template>

<script setup>
    import * as Session from '../session.js';
    import RegisterForm from '../components/RegisterForm.vue';
    import router from '@/router/index.js';

    if (Session.hasActiveSession()) {
        router.push('/');
    }

</script>