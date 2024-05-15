<template>
    <table class="table is-fullwidth is-hoverable is-striped" v-if="!isloading">
        <thead>
            <tr>
            <th class="has-text-centered">Position</th>
            <th class="has-text-centered">User</th>
            <th class="has-text-centered">Points</th>
            </tr>
        </thead>
        <tbody>
            <tr v-for="user in leaderboard" :key="user.id" v-bind:class="{ 'is-warning': user.username==username, 'has-text-weight-bold': user.username==username}">
                <td class="has-text-centered">{{user.position}}</td>
                <td class="has-text-centered">{{user.username}}</td>
                <td class="has-text-centered">{{user.points}}</td>
            </tr>
        </tbody>
    </table>
    <section class="section">
        <div class="columns is-centered">
            <div class="column is-2">
                <button class="button is-loading is-text is-large" v-if="isloading"></button>
            </div>
        </div>
    </section>
</template>

<script>
import * as Session from '../session.js';
import router from '@/router/index.js';

var leaderboard = [  /*{position: 1, username: 'usr_1', points: 100},
                        {position: 2, username: 'usr_2', points: 90},
                        {position: 3, username: 'usr_3', points: 80},
                        {position: 4, username: 'usr_4', points: 70},
                        {position: 5, username: 'usr_5', points: 60},
                        {position: 6, username: 'usr_6', points: 50},
                        {position: 7, username: 'abc12', points: 40},
                        {position: 8, username: 'usr_8', points: 30},
                        {position: 9, username: 'usr_9', points: 20},
                        {position: 10, username: 'usr_10', points: 10}*/
                     ];
var username = Session.getSessionUsername();

export default {
  data() {
    return {
        isloading: true,
        leaderboard,
        username,
        error: ''
    };
  },
  created() {
    this.getLeaderBoard();
  },
  methods: {
    async getLeaderBoard() {
        try {
            const response = await fetch(process.env.VUE_APP_API_URL+'/leaderboard', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                username: this.username,
                sessionkey: Session.getSessionKey()
            })
            });

            if (!response.ok) {
                let message = await response.json();
                if (message.error === "session is expired") {
                    Session.clearSession();
                    router.push('/login');
                }
                return;
            } else {
                this.leaderboard = await response.json();
                this.isloading = false;
            }
        } catch (error) {
            console.log(error);
        }
    }
  }
};
</script>