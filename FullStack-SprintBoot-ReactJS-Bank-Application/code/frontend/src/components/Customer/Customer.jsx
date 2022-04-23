import React, { useState, useEffect } from 'react';
import { Container, Row, Col, Button, Card } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import 'bootstrap/dist/css/bootstrap.css';

const styleForHorizontalCenter = {
  position: 'absolute',
  top: '50%',
  transform: 'translateY(-50%)',
  textAlign: 'center',
};

const paperStyle = {
  padding: '50px 20px',
  width: 600,
  margin: '20px auto',
  backgroundColor: '#9fa09e',
};

//need to add a view/button that allows a user to
//open a new account
export default function Customer(props) {
  const [numAccounts, setNumAccounts] = useState(0);
  const navigate = useNavigate();
  let user = JSON.parse(localStorage.getItem('user'));

  useEffect(() => {
    if (props.user.id === 0) {
      navigate('/');
      localStorage.setItem('isAuthenticated', false);
    }
  }, [props.user]);

  useEffect(() => {
    var config = {
      method: 'get',
      url: `http://localhost:8080/api/bankAccount/getAllBankAccount?userId=${props.user.id}`,
      headers: {
        Authorization: props.user.access_token,
      },
      data: '',
    };

    axios(config)
      .then(function (response) {
        setNumAccounts(response.data?.length);
      })
      .catch(function (error) {
        if (error.response.status === 403) {
          var config = {
            method: 'get',
            url: 'http://localhost:8080/api/token/refresh',
            headers: {
              Authorization: props.user.refresh_token,
            },
            data: '',
          };

          axios(config)
            .then(function (response) {
              let updateUser = props.user;
              updateUser.access_token = response.data.access_token;
              updateUser.refresh_token = response.data.refresh_token;
            })
            .catch(function (error) {
              navigate('/');
              localStorage.setItem('isAuthenticated', false);
            });
        }
      });
  }, []);

  const handleSubmit = (e) => {
    e.preventDefault();
    localStorage.setItem('isAuthenticated', false);
    navigate('/');
  };

  const handleCreate = (e) => {
    e.preventDefault();
    var config = {
      method: 'post',
      url: `http://localhost:8080/api/users/createAccount?userId=${props.user?.id}`,
      headers: {
        Authorization: props.user?.access_token,
      },
    };

    axios(config)
      .then(function (response) {
        navigate('/customer/balance');
      })
      .catch(function (error) {
        if (error.response.status === 403) {
          var config = {
            method: 'get',
            url: 'http://localhost:8080/api/token/refresh',
            headers: {
              Authorization: props.user.refresh_token,
            },
            data: '',
          };

          axios(config)
            .then(function (response) {
              let updateUser = props.user;
              updateUser.access_token = response.data.access_token;
              updateUser.refresh_token = response.data.refresh_token;
            })
            .catch(function (error) {
              navigate('/');
              localStorage.setItem('isAuthenticated', false);
            });
        }
      });
  };

  return (
    <Container fluid style={{ ...styleForHorizontalCenter }}>
      <Row>
        <Col>
          <Card style={paperStyle}>
            <div style={{ color: 'black' }}>
              <h1>
                Hello{' '}
                <span style={{ color: 'green' }}>
                  {props.user?.firstName} {props.user?.lastName}
                </span>
                !
              </h1>
              <br />
              <h2>Welcome to our banking app!</h2>
              <br />
              <h3>What has brought you here today?</h3>
              <br />
              <h4
                style={{
                  color: 'var(--bs-success)',
                  textShadow: '0pc 1px 5px white',
                  textDecoration: 'underline',
                }}
              >
                You currently have{' '}
                <span style={{ color: 'red' }}>
                  <b>{numAccounts}</b>
                </span>{' '}
                accounts with us!
              </h4>
              <br />
            </div>
            <Button
              variant='secondary'
              size='lg'
              style={{ width: '15rem', margin: '0 auto' }}
              onClick={handleCreate}
            >
              Add New Account +
            </Button>
            <br />
            <Button
              variant='primary'
              size='lg'
              style={{ width: '10rem', margin: '0 auto' }}
              onClick={() => {
                navigate('/customer/deposit');
              }}
            >
              Deposit
            </Button>{' '}
            <br />
            <Button
              variant='primary'
              size='lg'
              style={{ width: '10rem', margin: '0 auto' }}
              onClick={() => {
                navigate('/customer/withdraw');
              }}
            >
              Withdraw
            </Button>{' '}
            <br />
            <Button
              variant='primary'
              size='lg'
              style={{ width: '10rem', margin: '0 auto' }}
              onClick={() => {
                navigate('/customer/transfer');
              }}
            >
              Transfer
            </Button>{' '}
            <br />
            <Button
              variant='primary'
              size='lg'
              style={{ width: '11rem', margin: '0 auto' }}
              onClick={() => {
                navigate('/customer/balance');
              }}
            >
              Check Balance
            </Button>
            <br />
            <Button
              onClick={handleSubmit}
              variant='success'
              size='lg'
              style={{ width: '15rem', margin: '0 auto' }}
            >
              Logout
            </Button>
          </Card>
        </Col>
      </Row>
    </Container>
  );
}
