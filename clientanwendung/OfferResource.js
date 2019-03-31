class OfferResource {

    /**
     * Konstruktor.
     * @param {String} url Basis-URL des REST-Webservices (optional)
     */
    constructor(url) {
        this.url = url || "https://localhost:8443/Nachhilfefinder/api/Offers/";
        this.username = "";
        this.password = "";
    }

    /**
     * Benutzername und Passwort für die Authentifizierung merken.
     * @param {String} username Benutzername
     * @param {String} password Passwort
     */
    setAuthData(username, password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Songs suchen.
     * @param {String} query Suchparameter (optional)
     * @returns {Promise} Gefundene Songs
     */
    async findOffers(query) {
        let url = this.url;

        if (query !== undefined) {
            url += "?query=" + encodeURI(query);
        }

        let response = await fetch(url, {
            headers: {
                "accept": "application/json"
            }
        });

        return await response.json();
    }

    

    /**
     * Einzelnen Song auslesen.
     * @param {Number} id Song-ID
     * @returns {Promise} Gefundener Song
     */
    async getOffer(id) {
        let response = await fetch(this.url + id + "/", {
            headers: {
                "accept": "application/json",
                "authorization": "Basic " + btoa(this.username + ":" + this.password)
            }
        });

        return await response.json();
    }
}