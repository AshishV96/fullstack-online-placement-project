import { Carousel, Image, Row } from 'react-bootstrap';
import Container from 'react-bootstrap/Container';
import Header from './Header';

function Caro() {
    return (
            <><Header/>
            <Container fluid className='overflow-none' >
                <Row>
                    <Carousel>
                        <Carousel.Item>
                            <img
                                className="w-100"
                                src={require('..//images/job_search_portals.png')}
                                alt=""
                            />
                        </Carousel.Item>
                        <Carousel.Item>
                            <img
                                className="w-100"
                                src={require('..//images/pexels-fauxels-3184465.jpg')}
                                alt=""
                            />
                        </Carousel.Item>
                    </Carousel>
                </Row>
            </Container>
          </>
    
    )
}

export default Caro