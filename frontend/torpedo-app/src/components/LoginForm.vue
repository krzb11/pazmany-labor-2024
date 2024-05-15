<template>
    <section class="container">
        <form @submit.prevent="loginUser">
            <div class="field">
                <div class="control has-icons-left has-icons-right">
                    <input class="input" type="text" placeholder="Username" v-on:input="checkInputs('username')" v-model.trim="username" required>
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
                <div class="control">
                    <button class="button is-link" type="submit"> Login </button>
                </div>
            </div>        
        </form>
    </section>
</template>

<script>
import sha256 from 'crypto-js/sha256';

export default {
  data() {
    return {
      username: '',
      password: '',
      error: '',
      username_error: false,
      password_error: false,
      username_success: false,
      password_success: false
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
        }
        
        if (!this.username_success || !this.password_success) {
            return false;
        }

        return true;
    },
    async loginUser() {
        if (!this.checkInputs()) {
            return;
        }

        try {
            const response = await fetch(process.env.VUE_APP_API_URL+'/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                username: this.username,
                password: sha256(this.password).toString()
            })
            });

            if (!response.ok) {
                let message = await response.json();
                if (message.error === "username is unknown") {
                    this.username_error = "No user with this username.";
                    this.username_success = false;
                } else if (message.error === "password is wrong") {
                    this.password_error = "Wrong password.";
                    this.password_success = false;
                }
                return;
            }

            // Redirect or do something else after successful login
            // this.$router.push('/dashboard');
        } catch (error) {
            this.error = error.message;
        }
    }
  }
};
</script>