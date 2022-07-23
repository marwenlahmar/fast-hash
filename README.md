<div id="top"></div>



<!-- PROJECT SHIELDS -->
[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![MIT License][license-shield]][license-url]
[![LinkedIn][linkedin-shield]][linkedin-url]



<!-- PROJECT LOGO -->
<br />
<div align="center">

<h3 align="center">Fast Hash</h3>

  <p align="center">
    A fast solution to calculate hash for large files in Java
</div>



<!-- ABOUT THE PROJECT -->
## About The Project

Although most algorithms are efficient enough when calculating a hash for a single file, you will experience a noticeable performance impact when hashing multiple large files, the issue comes from the fact that they read the whole file at once<br>
Fast Hash purpose is to obtain good speed compared to others by reusing any hashing algorithm you want but applying it only on specific chunks of the file <br>

Fast Hash internally use MessageDigest to calculate the hash, you can use any algorithm supported by MessageDigest such as MD5, SHA1, SHA-256...

Fast Hash is inspired by Oshash but implemented differently, here's how it works:
* It reads up to a configurable chunk size from the beginning 
* It reads the file size
* It reads up to a configurable chunk size from the end
* Digest the 3 parts and generate a unique hash for the file

You can use SHA1 algorithm and a chunk size of 64 * 1024 which should be good for most cases


<!-- GETTING STARTED -->
## Getting Started

This project is built with Maven

### Prerequisites

JDK 11+ and Maven

### Installation

1. Clone the repo
   ```sh
   git clone https://github.com/marwenlahmar/fast-hash.git
   ```
2. Install
   ```sh
   mvn clean install
   ```

<p align="right">(<a href="#top">back to top</a>)</p>



<!-- USAGE EXAMPLES -->
## Usage

To get an instance of Fast Hash
```java
FastHash fastHash = FastHash.getInstance("SHA1", 64 * 1024);
```
_Fast Hash instances are thread safe_

To calculate the hash
```java
String hash = fastHash.digest(seekableDataStream);
```
_Fast Hash takes a SeekableDataStream interface as argument_
_Fast Hash provides two implementations for SeekableDataStream: ByteArrayDataStream and FileDataStream_

<p align="right">(<a href="#top">back to top</a>)</p>




<!-- CONTRIBUTING -->
## Contributing

If you have a suggestion that would make this better, please fork the repo and create a pull request. You can also simply open an issue with the tag "enhancement".
Don't forget to give the project a star! Thanks again!

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

<p align="right">(<a href="#top">back to top</a>)</p>



<!-- LICENSE -->
## License

Distributed under the MIT License. See `LICENSE` for more information.

<p align="right">(<a href="#top">back to top</a>)</p>



<!-- CONTACT -->
## Contact

Marwen Lahmar - [@lahmar_marwen](https://twitter.com/lahmar_marwen) - marwen.lahmar@gmail.com

Project Link: [https://github.com/marwenlahmar/fast-hash](https://github.com/marwenlahmar/fast-hash)

<p align="right">(<a href="#top">back to top</a>)</p>




<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[contributors-shield]: https://img.shields.io/github/contributors/marwenlahmar/fast-hash.svg?style=for-the-badge
[contributors-url]: https://github.com/marwenlahmar/fast-hash/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/marwenlahmar/fast-hash.svg?style=for-the-badge
[forks-url]: https://github.com/marwenlahmar/fast-hash/network/members
[stars-shield]: https://img.shields.io/github/stars/marwenlahmar/fast-hash.svg?style=for-the-badge
[stars-url]: https://github.com/marwenlahmar/fast-hash/stargazers
[issues-shield]: https://img.shields.io/github/issues/marwenlahmar/fast-hash.svg?style=for-the-badge
[issues-url]: https://github.com/marwenlahmar/fast-hash/issues
[license-shield]: https://img.shields.io/github/license/marwenlahmar/fast-hash.svg?style=for-the-badge
[license-url]: https://github.com/marwenlahmar/fast-hash/blob/master/LICENSE
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]: https://www.linkedin.com/in/marwen-lahmar-86b404a5/