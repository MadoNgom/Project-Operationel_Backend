# API Transactions - Sécurisée avec JWT

Cette API est conçue pour une application mobile money et est sécurisée avec des tokens JWT.

## 🚀 Démarrage rapide

1. **Démarrer l'application** : `mvn spring-boot:run`
2. **Accéder à Swagger** : http://localhost:8080/swagger-ui.html
3. **Tester l'API** : Utilisez Swagger ou Postman

## 🔐 Authentification JWT

### 1. Inscription d'un utilisateur
```http
POST /api/auth/register
Content-Type: application/json

{
    "nom": "Doe",
    "prenom": "John",
    "email": "john.doe@example.com",
    "telephone": "+1234567890",
    "adresse": "123 Main St",
    "password": "motdepasse123"
}
```

**Réponse avec token JWT :**
```json
{
    "data": {
        "token": "eyJhbGciOiJIUzI1NiJ9...",
        "type": "Bearer"
    },
    "isSuccess": true,
    "message": "Inscription réussie ! Votre compte a été créé avec succès.",
    "timestamp": 1703123456789,
    "status": "SUCCESS"
}
```

**✨ Fonctionnalité automatique :** Lors de l'inscription, un compte bancaire est automatiquement créé avec :
- Numéro de compte unique à 4 chiffres
- Solde initial à 0 CFA
- Devise par défaut : CFA
- Statut actif

**📋 Format de réponse standardisé :** Toutes les réponses suivent le format :
```json
{
    "data": {...},           // Données de la réponse
    "isSuccess": true,       // Statut de l'opération
    "message": "...",        // Message descriptif
    "timestamp": 1234567890, // Horodatage
    "status": "SUCCESS"      // Statut textuel
}
```

### 2. Connexion
```http
POST /api/auth/login
Content-Type: application/json

{
    "email": "john.doe@example.com",
    "password": "motdepasse123"
}
```

**Réponse avec token JWT :**
```json
{
    "data": {
        "token": "eyJhbGciOiJIUzI1NiJ9...",
        "type": "Bearer"
    },
    "isSuccess": true,
    "message": "Connexion réussie",
    "timestamp": 1703123456789,
    "status": "SUCCESS"
}
```

## 📚 Utilisation de Swagger

### Configuration de l'authentification
1. **Ouvrez Swagger** : http://localhost:8080/swagger-ui.html
2. **Cliquez sur le bouton "Authorize"** (🔒) en haut à droite
3. **Entrez votre token JWT** : `Bearer eyJhbGciOiJIUzI1NiJ9...`
4. **Cliquez sur "Authorize"**

### Endpoints disponibles

#### 🔓 Public (sans authentification)
- `POST /api/auth/register` - Inscription avec création automatique de compte
- `POST /api/auth/login` - Connexion
- `GET /api/test/public` - Test public
- `GET /swagger-ui/**` - Interface Swagger

#### 🔒 Protégés (avec authentification JWT)
- `GET /api/utilisateurs/me` - Profil utilisateur connecté avec informations du compte
- `GET /api/utilisateurs/{id}` - Détails d'un utilisateur
- `GET /api/utilisateurs` - Liste des utilisateurs
- `POST /api/utilisateurs` - Créer un utilisateur
- `DELETE /api/utilisateurs/{id}` - Supprimer un utilisateur
- `GET /api/comptes/me` - Compte de l'utilisateur connecté
- `GET /api/comptes/{numeroCompte}` - Compte par numéro
- `GET /api/comptes/utilisateur/{id}` - Compte d'un utilisateur
- `PUT /api/comptes/{id}/solde` - Mettre à jour le solde
- `GET /api/comptes/{id}/status` - Statut d'un compte
- `GET /api/test/protected` - Test protégé

## 🏦 Gestion des Comptes

### Création automatique
- **Lors de l'inscription** : Un compte est automatiquement créé
- **Numéro unique** : Génération automatique d'un numéro à 4 chiffres
- **Configuration par défaut** : Solde 0, devise CFA, statut actif

### Opérations disponibles
- **Consultation** : Récupérer les informations d'un compte
- **Mise à jour du solde** : Modifier le solde d'un compte
- **Vérification du statut** : Vérifier si un compte est actif

## 📋 Format des Réponses

Toutes les réponses de l'API suivent un format standardisé :

### Réponse de succès
```json
{
    "data": {
        // Données de la réponse
    },
    "isSuccess": true,
    "message": "Message de succès",
    "timestamp": 1703123456789,
    "status": "SUCCESS"
}
```

### Réponse d'erreur
```json
{
    "data": null,
    "isSuccess": false,
    "message": "Message d'erreur",
    "timestamp": 1703123456789,
    "status": "ERROR"
}
```

## 🛡️ Sécurité

- **JWT** : Tokens stateless avec expiration configurable
- **BCrypt** : Chiffrement des mots de passe
- **Stateless** : Pas de sessions côté serveur
- **CSRF** : Désactivé pour l'API REST
- **Authentification requise** : Tous les endpoints (sauf auth) nécessitent un token JWT

## ⚙️ Configuration

### Propriétés JWT (application.properties)
```properties
jwt.secret=404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970
jwt.expiration=86400000
```

- **secret** : Clé secrète pour signer les tokens
- **expiration** : Durée de vie en millisecondes (24h)

## 🧪 Test de l'API

### Avec Swagger
1. **Inscrivez-vous** via `/api/auth/register`
   - Vérifiez que le compte est créé automatiquement
   - Notez le numéro de compte généré
2. **Connectez-vous** via `/api/auth/login`
3. **Copiez le token** de la réponse
4. **Cliquez sur "Authorize"** dans Swagger
5. **Collez le token** : `Bearer <votre_token>`
6. **Testez les endpoints protégés**
   - Consultez votre profil : `/api/utilisateurs/me`
   - Consultez votre compte : `/api/comptes/me`

### Avec Postman/cURL
```bash
# 1. Inscription (création automatique du compte)
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"nom":"Test","prenom":"User","email":"test@example.com","password":"password123"}'

# 2. Connexion
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"test@example.com","password":"password123"}'

# 3. Utilisation du token pour accéder au profil (avec informations du compte)
curl -X GET http://localhost:8080/api/utilisateurs/me \
  -H "Authorization: Bearer <votre_token>"

# 4. Consulter le compte créé
curl -X GET http://localhost:8080/api/comptes/me \
  -H "Authorization: Bearer <votre_token>"
```

**Exemple de réponse pour `/api/utilisateurs/me` :**
```json
{
    "data": {
        "id": 1,
        "nom": "Test",
        "prenom": "User",
        "email": "test@example.com",
        "telephone": "+1234567890",
        "adresse": "123 Test St",
        "compte": {
            "id": 1,
            "numeroCompte": "1234",
            "solde": 0.0,
            "devis": "CFA",
            "actif": true
        }
    },
    "isSuccess": true,
    "message": "Profil récupéré avec succès",
    "timestamp": 1703123456789
}
```

## 🚨 Gestion des erreurs

- **400 Bad Request** : Données invalides
- **401 Unauthorized** : Token manquant ou invalide
- **403 Forbidden** : Accès refusé
- **404 Not Found** : Ressource introuvable

## 📝 Notes importantes

- **Création automatique** : Un compte bancaire est créé à chaque inscription
- **Numéros uniques** : Les numéros de compte sont générés automatiquement
- **Solde initial** : Tous les nouveaux comptes commencent avec 0 CFA
- **Devise par défaut** : CFA (Franc CFA)
- **Statut actif** : Tous les nouveaux comptes sont actifs par défaut
- **Tokens JWT** : Expirent après 24h
- **Préfixe Bearer** : Utilisez toujours `Bearer <token>` dans l'en-tête Authorization
- **Email unique** : Sert d'identifiant unique pour l'authentification
- **Mots de passe** : Sont automatiquement chiffrés avec BCrypt

## 🔄 Workflow complet

1. **Inscription** → Création utilisateur + compte automatique
2. **Connexion** → Obtention du token JWT
3. **Authentification** → Utilisation du token pour les requêtes protégées
4. **Gestion du compte** → Consultation, mise à jour du solde, etc.

---

**🎉 Votre API est maintenant prête avec création automatique de comptes bancaires !**
