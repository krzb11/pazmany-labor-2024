<template>
    <section class="section">
        <div class="columns is-centered">
            <div class="column is-6">
                <p class="title is-3 mb-3">
                    <span class="icon-text">
                        <span>Leaderboard </span>
                        <span class="icon has-text-warning pl-3">
                            <i class="fa fa-trophy"></i>
                        </span>
                    </span> 
                </p>
                <p class="subtitle is-6">Take a look at the best players around the World!</p>

                <div class="container">
                    <leader-board />
                </div>
            </div>
        </div>
    </section>
</template>

<script setup>
    import * as Session from '../session.js';
    import LeaderBoard from '../components/LeaderBoard.vue';
    import router from '@/router/index.js';

    if (!Session.hasActiveSession()) {
        router.push('/login');
    }

</script>