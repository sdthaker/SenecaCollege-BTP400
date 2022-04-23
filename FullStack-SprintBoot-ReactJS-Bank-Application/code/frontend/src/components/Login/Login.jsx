import axios from 'axios';
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useFormik } from 'formik';
import { Alert } from 'react-bootstrap';
import * as Yup from 'yup';
import qs from 'qs';
import './Login.scss';
import Cog from '../../cog.svg?component';

const mode = 'login';

function LoginComponent(props) {
  const [errorMessage, setErrorMessage] = useState('');

  return (
    <div>
      <div
        className={`form-block-wrapper form-block-wrapper--is-${props.mode}`}
      ></div>
      <section className={`form-block form-block--is-${props.mode}`}>
        <header className='form-block__header'>
          <h1>{mode === 'login' ? 'Welcome back!' : 'Sign up'}</h1>
          <div className='form-block__toggle-block'>
            <span>
              {mode === 'login' ? "Don't" : 'Already'} have an account? Click
              here &#8594;
            </span>
            <input
              id='form-toggler'
              type='checkbox'
              onClick={() =>
                props.setMode(props.mode === 'login' ? 'signup' : 'login')
              }
            />
            <label htmlFor='form-toggler'></label>
          </div>
        </header>
        <LoginForm
          mode={props.mode}
          setMode={props.setMode}
          setErrorMessage={setErrorMessage}
          setUser={props.setUser}
        />
        {errorMessage.length > 0 && <div><br /><Alert variant="danger" >{errorMessage}</Alert></div>}
      </section>
    </div>
  );
}

function LoginForm(props) {
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();
  const errorLoginStyle = { fontSize: "10pt", position: "absolute", transform: "translate(300px, -70px)" };
  const errorSignupStyle = { fontSize: "10pt", position: "absolute", transform: "translate(-128px, -70px)" };

  function submitLogin(values) {
    setLoading(true);
    axios({
      method: 'post',
      url: 'http://localhost:8080/login',
      headers: {
        Authorization: 'Basic Og==',
        'Content-Type': 'application/x-www-form-urlencoded',
      },
      data: qs.stringify({
        username: values.username,
        password: values.password,
      }),
    })
      //api call returns 200, credentials are valid
      .then((res) => {
        localStorage.setItem('isAuthenticated', 'true');

        let user = {
          id: res.data?.user?.id,
          firstName: res.data?.user?.firstName,
          lastName: res.data?.user?.lastName,
          email: res.data?.user?.email,
          username: res.data?.user?.username,
          userRole: res.data?.user?.userRole,
          numAccounts: res.data?.user?.numAccounts,
          access_token: res.data?.access_token,
          refresh_token: res.data?.refresh_token,
        };

        props.setUser(user);

        props.setErrorMessage('');
        setLoading(false);
        navigate('/customer');
      })
      //else show error on frontend
      .catch((error) => {
        setLoading(false);
        props.setErrorMessage('Invalid username/password.');
      });
  }

  function submitSignup(values) {
    axios({
      method: 'post',
      url: 'http://localhost:8080/api/user/registration/register',
      headers: {
        'Content-Type': 'application/json',
      },
      data: JSON.stringify({
        firstName: values.firstName,
        lastName: values.lastName,
        email: values.email,
        username: values.usernameSignup,
        password: values.createPassword,
        dateOfBirth: values.dob,
      }),
    })
      .then((res) => {
        props.setErrorMessage('Please confirm email to login');
        props.setMode("login")
      })
      .catch((error) => {
        props.setErrorMessage('Invalid username/email');
      });
  }

  const formik = useFormik({
    initialValues: {
      username: '',
      password: '',

      firstName: '',
      lastName: '',
      email: '',
      usernameSignup : '',
      dob: '',
      createPassword: '',
      repeatPassword: '',
    },
    validationSchema: Yup.object({
      firstName: props.mode === "signup" ? Yup.string().max(30, "Must be less than 30 characters").required("Required") : null,
      lastName: props.mode === "signup" ? Yup.string().max(30, "Must be less than 30 characters").required("Required") : null,
      email: props.mode === "signup" ? Yup.string().email("Invalid email address").max(50, "Must be less than 50 characters").required("Required") : null,
      createPassword: props.mode === "signup" ? Yup.string().min(8, "Password must be at least 8 characters").required("Required") : null,
      repeatPassword: props.mode === "signup" ? Yup.string().oneOf([Yup.ref('createPassword')], 'Passwords must match').required("Required") : null,
      usernameSignup: props.mode === "signup" ? Yup.string().min(4, "Username must be at least 4 characters").max(30, "Must be less than 30 characters").required("Required") : null,
      username: props.mode === "login" ? Yup.string().required("Required") : null,
      password: props.mode === "login" ? Yup.string().required("Required") : null
    }),
    onSubmit: (values) => {
      if (props.mode === 'login')
        submitLogin(values);
      else if (props.mode === 'signup')
        submitSignup(values);
    },
  });

  return (
    <form onSubmit={formik.handleSubmit}>
      <div className='form-block__input-wrapper'>
        <div className='form-group form-group--login'>
          <Input
            type='text'
            id='username'
            label='username'
            onChange={formik.handleChange}
            value={formik.values.username}
          />
          {formik.errors.username && <Alert variant="danger" style={errorLoginStyle}>{formik.errors.username}</Alert>}

          <Input
            type='password'
            id='password'
            label='password'
            disabled={props.mode === 'signup'}
            onChange={formik.handleChange}
            value={formik.values.password}
          />
          {formik.errors.password && <Alert variant="danger" style={errorLoginStyle}>{formik.errors.password}</Alert>}
        </div>

        <div className='form-group form-group--signup'>
          <Input
            type='text'
            id='firstName'
            label='first name'
            disabled={props.mode === 'login'}
            onChange={formik.handleChange}
            value={formik.values.firstName}
          />
          {formik.errors.firstName && <Alert variant="danger" style={errorSignupStyle}>{formik.errors.firstName}</Alert>}
          <Input
            type='text'
            id='lastName'
            label='last name'
            disabled={props.mode === 'login'}
            onChange={formik.handleChange}
            value={formik.values.lastName}
          />
          {formik.errors.lastName && <Alert variant="danger" style={errorSignupStyle}>{formik.errors.lastName}</Alert>}
          <Input
            type='date'
            id='dob'
            label='date of birth'
            disabled={props.mode === 'login'}
            onChange={formik.handleChange}
            value={formik.values.dob}
          />
          <Input
            type='email'
            id='email'
            label='email'
            disabled={props.mode === 'login'}
            onChange={formik.handleChange}
            value={formik.values.email}
          />
          {formik.errors.email && <Alert variant="danger" style={errorSignupStyle}>{formik.errors.email}</Alert>}
          <Input
            type='text'
            id='usernameSignup'
            label='username'
            onChange={formik.handleChange}
            value={formik.values.usernameSignup}
          />
          {formik.errors.usernameSignup && <Alert variant="danger" style={errorSignupStyle}>{formik.errors.usernameSignup}</Alert>}
          <Input
            type='password'
            id='createPassword'
            label='password'
            disabled={props.mode === 'login'}
            onChange={formik.handleChange}
            value={formik.values.createPassword}
          />
          {formik.errors.createPassword && <Alert variant="danger" style={errorSignupStyle}>{formik.errors.createPassword}</Alert>}
          <Input
            type='password'
            id='repeatPassword'
            label='repeat password'
            disabled={props.mode === 'login'}
            onChange={formik.handleChange}
            value={formik.values.repeatPassword}
          />
          {formik.errors.repeatPassword && <Alert variant="danger" style={errorSignupStyle}>{formik.errors.repeatPassword}</Alert>}
        </div>
      </div>
      <button
        className='custom-button custom-button--primary full-width'
        type='submit'
      >
        {props.mode === 'login' ? 'Log In' : 'Sign Up'} &nbsp; {loading && <Cog className="logo-spin" style={{ width: "25px", height: "25px" }} />}
      </button>
    </form>
  );
}

const Input = ({ id, type, label, disabled, onChange, value }) => (
  <input
    className='form-group__input'
    type={type}
    id={id}
    placeholder={label}
    disabled={disabled}
    onChange={onChange}
    value={value}
  />
);

function Login(props) {
  const [currentMode, setMode] = useState(mode);
  return (
    <div className={`custom-login app--is-${currentMode}`}>
      <LoginComponent
        mode={currentMode}
        setMode={setMode}
        setUser={props.setUser}
      />
    </div>
  );
}

export default Login;
