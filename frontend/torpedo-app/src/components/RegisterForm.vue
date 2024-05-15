<template>
    <section class="container">
        <form @submit.prevent="loginUser">
            <div class="field">
                <div class="control has-icons-left has-icons-right">
                    <input class="input" type="text" placeholder="Username" v-on:input="checkInputs('username')" v-model.trim="username" value="{{ username }}" required>
                    <span class="icon is-small is-left">
                        <i class="fa fa-user"></i>
                    </span>
                    <span class="icon is-small is-right">
                        <i class="fa fa-check" v-if="username_success"></i>
                    </span>
                </div>
                <p class="help is-error" v-if="username_error"> {{ username_error }} </p>
            </div>

            <div class="field">
                <div class="control has-icons-left has-icons-right">
                    <input class="input" type="email" placeholder="Email" v-on:input="checkInputs('email')" v-model.trim="email" value="{{ email }}" required>
                    <span class="icon is-small is-left">
                        <i class="fa fa-at"></i>
                    </span>
                    <span class="icon is-small is-right">
                        <i class="fa fa-check" v-if="email_success"></i>
                    </span>
                </div>
                <p class="help is-error" v-if="email_error"> {{ email_error }} </p>
            </div>
            
            <div class="field">
                <div class="control has-icons-left has-icons-right">
                    <input class="input" type="password" placeholder="Password" v-on:input="checkInputs('password')" v-model.trim="password" required />
                    <span class="icon is-small is-left">
                        <i class="fa fa-lock"></i>
                    </span>
                    <span class="icon is-small is-right">
                        <i class="fa fa-check" v-if="password_success"></i>
                    </span>
                </div>
                <p class="help is-error" v-if="password_error"> {{ password_error }} </p>
            </div>

            <div class="field">
                <div class="control has-icons-left has-icons-right">
                    <input class="input" type="password" placeholder="Password again" v-on:input="checkInputs('password')" v-model.trim="passwordagain" required />
                    <span class="icon is-small is-left">
                        <i class="fa fa-lock"></i>
                    </span>
                    <span class="icon is-small is-right">
                        <i class="fa fa-check" v-if="passwordagain_success"></i>
                    </span>
                </div>
                <p class="help is-error" v-if="passwordagain_error"> {{ passwordagain_error }} </p>
            </div>
            
            <div class="field is-grouped">
                <div class="control">
                    <button class="button is-link" type="submit"> Sign up </button>
                </div>
                <div class="control">
                    <button class="button is-light is-outlined" v-on:click="routeLogin()"> Log in </button>
                </div>
            </div>        
        </form>
    </section>
</template>

<script>
import sha256 from 'crypto-js/sha256';
import * as Session from '../session.js';
import router from '@/router/index.js';

export default {
  data() {
    return {
      username: '',
      email: '',
      password: '',
      passwordagain: '',
      username_error: false,
      email_error: false,
      password_error: false,
      passwordagain_error: false,
      username_success: false,
      email_success: false,
      password_success: false,
      passwordagain_success: false,
      error: ''
    };
  },
  methods: {
    checkInputs(field='all') {
        if (field == 'all' || field == 'username') {
            if (/^(?=.*[a-zA-Z])[a-zA-Z0-9_]{5,10}$/.test(this.username)) {
                this.username_success = true;
                this.username_error = false;
            } else {
                this.username_success = false;
                if (!(/^(?=.*[a-zA-Z])/.test(this.username))) {
                    this.username_error = "Username must contain at least one letter!";
                } else if (!/[a-zA-Z0-9_]+$/.test(this.username)) {
                    this.username_error = "Allowed characters: a-z, A-Z, 0-9, _";
                } else {
                    this.username_error = "Username must be 5-10 long!";
                }
            }
        }

        if (field == 'all' || field == 'email') {
            if (/^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/.test(this.email)) {
                this.email_success = true;
                this.email_error = false;
            } else {
                this.email_success = false;
                this.email_error = "Incorrect email address!";
            }
        }

        if (field == 'all' || field == 'password') {
            if (/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[.,;_#?!@$%^&*-])[a-zA-Z0-9.,;_#?!@$%^&*-]{8,18}$/.test(this.password)) {
                this.password_success = true;
                this.password_error = false;
            } else {
                this.password_success = false;
                if (!(/(?=.*[a-z])/.test(this.password))) {
                    this.password_error = "Must contain at least one lowercase letter!";
                } else if (!(/(?=.*[A-Z])/.test(this.password))) {
                    this.password_error = "Must contain at least one uppercase letter!";
                } else if (!(/(?=.*[0-9])/.test(this.password))) {
                    this.password_error = "Must contain at least one number!";
                } else if (!(/(?=.*[.,;_#?!@$%^&*-])/.test(this.password))) {
                    this.password_error = "Must contain at least one special character!";
                } else if (!(/[a-zA-Z0-9.,;_#?!@$%^&*-]+$/.test(this.password))) {
                    this.password_error = "Allowed characters: a-z, A-Z, 0-9, .,;_#?!@$%^&*-";
                } else {
                    this.password_error = "Username must be 8-18 long!";
                }
            }

            if (this.password != this.passwordagain) {
                this.passwordagain_success = false;
                this.passwordagain_error = "Passwords do not match!";
            } else {
                this.passwordagain_success = true;
                this.passwordagain_error = false;
            }
        }
        
        if (!this.username_success || !this.password_success || !this.passwordagain_success || !this.email_success) {
            return false;
        }

        return true;
    },
    routeLogin() {
        router.push('/login');
    },
    async loginUser() {
        if (!this.checkInputs()) {
            return;
        }

        try {
            const response = await fetch(process.env.VUE_APP_API_URL+'/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                username: this.username,
                email: this.email,
                password: sha256(this.password).toString()
            })
            });

            if (!response.ok) {
                let message = await response.json();
                if (message.error === "username already taken") {
                    this.username_error = "This username is alredy taken.";
                    this.username_success = false;
                } else if (message.error === "email already taken") {
                    this.email_error = "This email address is already registered.";
                    this.email_success = false;
                }
                return;
            } else {
                let session = await response.json();
                Session.addSession(JSON.stringify(session));
                router.push('/');
            }
        } catch (error) {
            this.error = error.message;
        }
    }
  }
};
</script>