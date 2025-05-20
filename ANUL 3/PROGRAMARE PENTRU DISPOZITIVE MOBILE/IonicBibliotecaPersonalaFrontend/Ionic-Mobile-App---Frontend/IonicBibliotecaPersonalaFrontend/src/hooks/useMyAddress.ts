import { Geolocation } from '@capacitor/geolocation';
import { mapsApiKey } from '../components/mapsApiKey';

export function useMyAddress(){
        
async function getAddressFromCoordinates(lat: number, lng: number): Promise<string | null> {
    const apiKey = mapsApiKey;
    const url = `https://maps.googleapis.com/maps/api/geocode/json?latlng=${lat},${lng}&key=${apiKey}`;
  
    try {
      const response = await fetch(url);
      const data = await response.json();
      
      if (data.status === 'OK' && data.results.length > 0) {
        return data.results[0].formatted_address; // Returns the first formatted address
      } else {
        console.error('Geocoding API error:', data.status);
        return null;
      }
    } catch (error) {
      console.error('Error fetching address:', error);
      return null;
    }
  }

  return getAddressFromCoordinates;
}

