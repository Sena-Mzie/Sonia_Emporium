Sonia Emporium - Luxury Fashion E-commerce Mobile App

Overview
Sonia Emporium is a luxury fashion mobile application offering a seamless shopping experience for users. 
With an elegant design and robust functionalities, users can explore premium fashion collections, manage their profiles, and complete purchases—all in the palm of their hands. 
The application is built using Kotlin and integrates with Firebase for data management and authentication.


Features
User Features
- Shop Page: Browse luxury fashion items categorized.
- Product Details: View detailed information about products and see recommended items based on the category.
- Cart Management: Add items to the cart, update quantities, and remove items. Prices and totals adjust dynamically, including a 10% delivery fee.
- Checkout: Securely proceed to checkout to confirm delivery details and choose a payment method (cash on delivery, online payment, or EFT).
- Profile Management: View user details (name and email) and log out.
- Appointment Booking: Schedule consultations for personalized fashion items by providing contact details and selecting a date and time.

Responsive Design
- Designed for all mobile screen sizes with a user-friendly interface.


Technologies Used
- Frontend: Kotlin with XML for UI design.
- Backend: Firebase Firestore and Authentication for real-time data storage and secure login.
- Development Environment: Android Studio.

Pages and Functionalities
1. Home Page
   - Highlights featured collections and categories.

2. Shop Page
   - Displays categories in a scrollable view.
   - Dynamically loads product details from Firebase Firestore.

3. Product Details Page
   - Provides comprehensive details about selected items.
   - Suggests related products based on the category.

4. Cart Page
   - Lists selected items with their names, prices, and quantities.
   - Calculates the total price, including a delivery fee.
   - Allows users to update or remove items.

5. Checkout Page
   - Collects delivery details, including address, date, and time.
   - Enables users to select payment options.
   - Accessible only if the cart is not empty.

6. Profile Page
   - Displays user details collected during registration or login.
   - Offers a logout button for security.

7. Appointment Booking Page
   - Collects user contact details, a date, and time for personalized fashion consultations.
   - Ensures the selected date is not earlier than the current date.

Setup and Installation
Requirements
- Android Studio
- Android device or emulator running **Android 6.0 (Marshmallow)** or higher

Steps
1. Clone the repository:
   ```bash
   git clone https://github.com/username/sonia-emporium-mobile.git
   cd sonia-emporium-mobile
   ```

2. Open the project in Android Studio.

3. Configure the Firebase project:
   - Set up Firestore and Authentication in your Firebase console.
   - Download the `google-services.json` file and place it in the `app/` directory of your project.

4. Build and run the app:
   - Connect a physical device or launch an emulator.
   - Press `Run` in Android Studio.

Firebase Configuration
Ensure Firebase Firestore is set up with the following collections:
- `products`: Contains product details, including name, price, category, size, and availability.
- `cart`: Tracks items added to the cart by each user.
- `users`: Stores user profiles and authentication details.
- `checkout`: Logs completed purchases, including the total price and items.
- `booking`: Saves appointment bookings with the user-provided details.

Testing
Thorough testing ensures the following functionalities are working as intended:
- User authentication and profile management.
- Real-time loading of product details and categories.
- Cart operations (add, update, remove).
- Secure checkout process.
- Appointment booking.


Enjoy a premium shopping experience with Sonia Emporium! 
