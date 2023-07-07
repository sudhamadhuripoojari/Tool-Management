import React, { useEffect } from 'react';

const UnsplashImages: React.FC = () => {
  useEffect(() => {
    const setRandomBackgroundImage = () => {
      fetch('https://source.unsplash.com/featured/?tools')
        .then((response) => {
          if (response.ok) {
            document.body.style.backgroundImage = `url('${response.url}')`;
            document.body.style.backgroundSize = 'cover';
            document.body.style.backgroundPosition = 'center';
          } else {
            console.log('Failed to fetch image');
          }
        })
        .catch((error) => {
          console.log('Error:', error);
        });
    };

    
    document.body.addEventListener('click', setRandomBackgroundImage);

    return () => {
      document.body.removeEventListener('click', setRandomBackgroundImage);
    };
  }, []);

  return <div></div>;
};

export default UnsplashImages;
